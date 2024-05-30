package util;

import contract.Tag;
import contract.Uploader;
import domainLogic.*;
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
    Admin ad = new Admin();

    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        try {
            Command cmd = new Command();
            Collection<Tag> t = cmd.parseTags("Animal,Review,Music,News");
            for (Tag tt : t) {
                System.out.println(tt);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error in main: " + e.getMessage());
        }
    }

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
        Uploader pName = new UploaderImpl(parts[1]);
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
                    System.out.println("Update erfolgreich: " + ad.getObj(location).getAddress() + " Zugriffe: " + ad.getObj(location).getAccessCount());
                }
            } else {
                System.out.println("Update fehlgeschlagen");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating audio: " + e.getMessage());
        }
    }
}