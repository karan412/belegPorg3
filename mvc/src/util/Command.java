package util;

import contract.Tag;
import domainLogic.*;
import fileSystem.FileSystem;
import handler.MediaEvents.*;
import handler.uploaderEvents.DeleteUploaderEvent;
import handler.uploaderEvents.InsertUploaderEvent;
import handler.uploaderEvents.ListUploaderMediaCountEvent;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Command Class for managing Media
 */
public class Command {

    /**
     * List of MediaContentImpl objects
     */
    public List<MediaContentImpl> list;
    /**
     * Admin instance
     */
    private Admin ad;
    /**
     * Capacity of the Admin
     */
    private long capacity;

    /**
     * FileSystem instance
     */
    private FileSystem fs;

    private EventHandler<InsertUploaderEvent> insertUploader;
    private EventHandler<DeleteUploaderEvent> deleteUploader;
    private EventHandler<ListUploaderMediaCountEvent> getAllMediaCount;
    private EventHandler<InsertMediaEvent> insertMedia;
    private EventHandler<DeleteMediaEvent> deleteMedia;
    private EventHandler<UpdateMediaEvent> updateMedia;
    private EventHandler<FilterMediaEvent> filterMedia;
    private EventHandler<TagMediaEvent> tagMedia;

    /**
     * Constructor
     *
     * @param capacity long
     */
    public Command(Admin ad, long capacity) {
        this.capacity = capacity;
        this.ad = ad;
        fs = new FileSystem(ad);
    }

    public void setUploaderInsertHandler(EventHandler<InsertUploaderEvent> insertUploader) {
        this.insertUploader = insertUploader;
    }

    public void setUploaderDeleteHandler(EventHandler<DeleteUploaderEvent> deleteUploader) {
        this.deleteUploader = deleteUploader;
    }

    public void setGetAllProducerMediaCountHandler(EventHandler<ListUploaderMediaCountEvent> getAllMediaCount) {
        this.getAllMediaCount = getAllMediaCount;
    }

    public void setMediaInsertHandler(EventHandler<InsertMediaEvent> insertMedia) {
        this.insertMedia = insertMedia;
    }

    public void setMediaDeleteHandler(EventHandler<DeleteMediaEvent> deleteMedia) {
        this.deleteMedia = deleteMedia;
    }

    public void setMediaUpdateHandler(EventHandler<UpdateMediaEvent> updateMedia) {
        this.updateMedia = updateMedia;
    }


    public void setFilterMediaHandler(EventHandler<FilterMediaEvent> filterMedia) {
        this.filterMedia = filterMedia;
    }

    public void setTagMediaHandler(EventHandler<TagMediaEvent> tagMedia) {
        this.tagMedia = tagMedia;
    }

    /**
     * Method to create a MediaContentImpl object from the input string
     *
     * @param input String
     * @return MediaContentImpl object
     */
    private MediaContentImpl createObject(String input) {
        long size;
        BigDecimal cost;
        Duration availability = Duration.ofDays(30);
        int resolution = 1080;
        int samplingRate = 44100;

        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input is empty!");
        }

        String[] parts = input.split("\\s+");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Incorrect input format!");
        }

        try {
            size = Long.parseLong(parts[3]);
            cost = new BigDecimal(parts[4]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Size and cost should be numeric values.");
        }

        String mediaType = parts[0];
        UploaderImpl pName = new UploaderImpl(parts[1]);
        Collection<Tag> tags = parseTags(parts[2]);

        String address = ad.generateAddress();

        try {
            if (parts.length > 5) {
                availability = Duration.ofDays(Long.parseLong(parts[5]));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Availability should be a whole number representing days.");
        }

        try {
            if (mediaType.equals("Audio") || mediaType.equals("AudioVideo")) {
                if (parts.length > 6) {
                    samplingRate = Integer.parseInt(parts[6]);
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Sampling rate should be a whole number.");
        }

        try {
            if (mediaType.equals("Video") || mediaType.equals("AudioVideo")) {
                if (parts.length > 7) {
                    resolution = Integer.parseInt(parts[7]);
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Resolution should be a whole number.");
        }


        return switch (mediaType) {
            case "Audio" -> new AudioImpl(mediaType, pName.getName(), address, tags, size, cost, availability,samplingRate);
            case "Video" -> new VideoImpl(mediaType, pName.getName(), address, tags, size, cost, availability, resolution);
            case "AudioVideo" ->
                    new AudioVideoImpl(mediaType, pName.getName(), address, tags, size, cost, availability, samplingRate, resolution);
            default -> throw new IllegalArgumentException("Invalid media type: " + mediaType);
        };
    }

    /**
     * Parse tags from the input string
     *
     * @param tagsStr String
     * @return Collection of tags
     */
    private Collection<Tag> parseTags(String tagsStr) {
        if (tagsStr.equals(",")) {
            return Collections.emptyList();
        }
        String[] tags = tagsStr.split(",");
        if (tags.length > 4) {
            throw new IllegalArgumentException("Too many tags! Maximum allowed is 4.");
        }

        Collection<Tag> tagList = new ArrayList<>();
        for (String tag : tags) {
            try {
                tagList.add(Tag.valueOf(tag));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid tag: " + tag);
            }
        }
        return tagList;
    }

    /**
     * Insert an audio object
     *
     * @param media String
     */
    public void insertMedia(String media) {
        try {
            if (media != null && !media.isEmpty()) {
                MediaContentImpl obj = createObject(media);
                InsertMediaEvent event = new InsertMediaEvent(this, obj);
                insertMedia.handle(event);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error inserting audio: " + e.getMessage());
        }
    }

    /**
     * Delete an Media object
     *
     * @param location String
     */
    public void deleteMedia(String location) {
        DeleteMediaEvent event = new DeleteMediaEvent(this, location);
        deleteMedia.handle(event);
    }

    /**
     * List all audio objects
     */
    public void displayScreen() {
        ListUploaderMediaCountEvent event1 = new ListUploaderMediaCountEvent(this);
        getAllMediaCount.handle(event1);

    }

    /**
     * List all media objects
     */
    public void displayAllContent(String type) {
        FilterMediaEvent event = new FilterMediaEvent(this, type);
        filterMedia.handle(event);

    }

    /**
     * List all given/not given Tags
     */
    public void displayTags(String mode) {
        TagMediaEvent event = new TagMediaEvent(this, mode);
        tagMedia.handle(event);
    }

    /**
     * Update an audio object
     *
     * @param location String
     */
    public void updateMedia(String location) {
        try {
            if (location != null && !location.isEmpty()) {
                UpdateMediaEvent event = new UpdateMediaEvent(this, location);
                updateMedia.handle(event);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating audio: " + e.getMessage());
        }
    }


    /**
     * Filter media by type, sends type to the FilterMediaEvent
     *
     * @param type String
     */
    public void filterMedia(String type) {
        FilterMediaEvent event = new FilterMediaEvent(this, type);
        filterMedia.handle(event);
    }

    /**
     * Insert an uploader
     *
     * @param name String
     */
    public void insertUploader(String name) {
        UploaderImpl uploader = new UploaderImpl(name);
        InsertUploaderEvent event = new InsertUploaderEvent(this, uploader);
        insertUploader.handle(event);
    }


    /**
     * Delete an uploader
     *
     * @param name String
     */
    public void deleteUploader(String name) {
        DeleteUploaderEvent event = new DeleteUploaderEvent(this, name);
        deleteUploader.handle(event);
    }

    /**
     * Save state of Admin with jos or jbp
     *
     * @param tech String
     */
    public void saveState(String tech) {
        switch (tech) {
            case "jos":
                // Save state
                fs.saveState("state.jos");
                break;
            case "jbp":
                System.out.println("JBP not implemented.");
                break;
            default:
                System.out.println("Unknown tech: " + tech);
        }
    }

    /**
     * Load state of Admin with the jos or jbp
     *
     * @param tech String
     */
    public void loadState(String tech) {
        switch (tech) {
            case "jos":
                // Load state
                Admin loadedAdmin = fs.loadState("state.jos");
                if (loadedAdmin != null) {
                    ad = loadedAdmin;
                }
                break;
            case "jbp":
                System.out.println("JBP not implemented.");
                break;
            default:
                System.out.println("Unknown tech: " + tech);
        }
    }


}