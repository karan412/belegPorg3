package util;

import contract.Tag;
import domainLogic.*;
import fileSystem.FileSystem;
import uploaderManger.MediaUploadable;

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
     * List of MediaUploadable objects
     */
    public List<MediaUploadable> list;
    /**
     * Admin instance
     */

    long defaultCapacity = 1000L;
    Admin ad = new Admin(defaultCapacity);

    FileSystem fs = new FileSystem(ad);

    /**
     * Method to create a MediaUploadable object from the input string
     *
     * @param input String
     * @return MediaUploadable object
     */
    private MediaUploadable createObject(String input) {
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
        long accessCount = 0;

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
            case "Audio" -> new AudioImpl(pName, tags, address, size, cost, availability, samplingRate, accessCount);
            case "Video" -> new VideoImpl(pName, tags, address, size, cost, availability, resolution, accessCount);
            case "AudioVideo" ->
                    new AudioVideoImpl(pName, tags, address, size, cost, availability, samplingRate, resolution, accessCount);
            default -> {
                System.out.println("Unknown media type!");
                yield null;
            }
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
     * @param audioInput String
     */
    public void insertAudio(String audioInput) {
        try {
            if (audioInput != null && !audioInput.isEmpty()) {
                String res = ad.insert(createObject(audioInput));
                System.out.println(res);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error inserting audio: " + e.getMessage());
        }
    }

    /**
     * Delete an audio object
     *
     * @param location String
     */
    public void deleteAudio(String location) {
        try {
            if (location != null && !location.isEmpty()) {
                if (ad.delete(location)) {
                    System.out.println("Delete erfolgreich");
                } else {
                    System.out.println("Delete fehlgeschlagen");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting audio: " + e.getMessage());
        }
    }

    /**
     * List all audio objects
     */
    public void listAudio() {
        try {
            int index = 1;
            String tags = "e";
            String content = "";
            list = ad.list();
            for (MediaUploadable media : list) {
                if (!media.getTags().isEmpty()) {
                    tags = "i";
                }
                if (media instanceof AudioImpl) {
                    content = "Audio";
                }
                if (media instanceof VideoImpl) {
                    content = "Video";
                }
                if (media instanceof AudioVideoImpl) content = "AudioVideo";
                System.out.println(index + "." + "Content: " + content + "\n  Tags:  " + tags + "\n  Uploader: " + media.getUploader().getName() + "\n  Address: " + media.getAddress() + "\n  AccessCount: " + media.getAccessCount() +
                        "\n  Size: " + media.getSize() + "\n  Availability: " + media.getAvailability() + "\n  Cost: " + media.getCost());
                index++;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error listing audio: " + e.getMessage());
        }
    }

    /**
     * Update an audio object
     *
     * @param location String
     */
    public void updateAudio(String location) {
        try {
            if (location != null && !location.isEmpty()) {
                if (ad.update(location)) {
                    System.out.println("Update Successfull");
                } else {
                    System.out.println("Update failed - Location not found");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating audio: " + e.getMessage());
        }
    }


    /**
     * Filter media by type
     *
     * @param type String
     */
    public void filterMedia(String type) {
        int index = 1;
        List<MediaUploadable> list = ad.filterMedia(type);
        if (!list.isEmpty()) {
            for (MediaUploadable media : list) {
                System.out.println(index + "." + "Content: " + type + "\n  Tags:  " + media.getTags() + "\n  Uploader: " + media.getUploader().getName() + "\n  Address: " + media.getAddress() + "\n  AccessCount: " + media.getAccessCount() +
                        "\n  Size: " + media.getSize() + "\n  Availability: " + media.getAvailability() + "\n  Cost: " + media.getCost());
                index++;
            }
        }
    }

    /**
     * Insert an uploader
     *
     * @param name String
     */
    public void insertUploader(String name) {
        UploaderImpl uploader = new UploaderImpl(name);
        boolean res = ad.insertUploader(uploader);
        if (res) {
            System.out.println("Uploader added successfully");
        } else {
            System.out.println("Uploader already exists");
        }
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
                fs.saveState("state_jos.txt");
                break;
            case "jbp":
                // TODO: Implement JBP logic here
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
                Admin loadedAdmin = fs.loadState("state_jos.txt");
                if (loadedAdmin != null) {
                    ad = loadedAdmin;
                }
                break;
            case "jbp":
                // TODO: Implement JBP logic here
                break;
            default:
                System.out.println("Unknown tech: " + tech);
        }
    }
}