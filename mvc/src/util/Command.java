package util;

import contract.Audio;
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
 * Command Klasse fuer die Verarbeitung von Befehlen
 * (AudioImpl wird hier nur mit 2 Parametern angelegt, um die Funktionalitaet zu testen)
 */
public class Command {

    /**
     * Admin Objekt
     */
    Admin ad = new Admin();

    /**
     * Liste von AudioImpl Objekten
     */
    private List<MediaUploadable> list;


    /**
     * Hilfsmethode zum Erstellen eines AudioImpl-Objekts nach Parsen des Inputs
     */
    private MediaUploadable createObject(String input) {
        if (input == null || input.isEmpty()) {
            System.out.println("Input is empty!");
            return null;
        }

        //[Media-Typ] [P-Name] [kommaseparierte Tags, einzelnes Komma für keine] [Größe] [Abrufkosten] [[Optionale Parameter]]
        String[] parts = input.split("\\s+");
        /*required fields: MediaType, P-Name, Tags, Size, Cost*/
        if (parts.length < 5) {
            System.out.println("Incorrect input format!");
           return null;
        }

        String mediaType = parts[0];
        Uploader pName = new UploaderImpl(parts[1]);
        Collection<Tag> tags = parseTags(parts[2]);
        long size = Long.parseLong(parts[3]);
        BigDecimal cost = new BigDecimal(parts[4]);

        // Default values for optional parameters
        String address = ad.generateAddress();
        int samplingRate = 44100;
        Duration availability = Duration.ofDays(30);
        long accessCount = 0;
        int resolution = 1080;


        int index = 5; // Starting index for optional parameters
        return switch (mediaType) {
            case "Audio" -> {
                if(parts.length > 5){
                    availability = Duration.parse(parts[5]);
                    samplingRate = Integer.parseInt(parts[6]);
                }
                yield new AudioImpl(pName, tags, address, size, cost, availability, samplingRate, accessCount);
            }
            case "Video" -> {
                if(parts.length > 5){
                    availability = Duration.parse(parts[5]);
                    resolution = Integer.parseInt(parts[6]);
                }
                yield new VideoImpl(pName, tags, address, size, cost, availability, resolution, accessCount);
            }
            case "AudioVideo" -> {
                if(parts.length > 5){
                    availability = Duration.parse(parts[5]);
                    samplingRate = Integer.parseInt(parts[6]);
                    resolution = Integer.parseInt(parts[7]);
                }
                yield new AudioVideoImpl(pName, tags, address, size, cost, availability, samplingRate, resolution, accessCount);
            }
            default -> {
                System.out.println("Unknown media type!");
                yield null;
            }
        };
    }


    /**
     * Hilfsmethode zum Parsen der Tags
     */
    private Collection<Tag> parseTags(String tagsStr) {
        if (tagsStr.equals(",")) {
            return Collections.emptyList();
        }
        String[] tags = tagsStr.split(",");
        if (tags.length > 4) {
            System.out.println("Too many tags! Maximum allowed is 4.");
            return Collections.emptyList(); // Or handle error appropriately
        }

        Collection<Tag> tagList = new ArrayList<>();
        for (String tag : tags) {
            try {
                tagList.add(Tag.valueOf(tag));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid tag: " + tag);
            }
        }
        return tagList;
    }

    /**
     * Methode zum Einfuegen eines AudioImpl-Objekts
     */
    public void insertAudio(String audioInput) {
        if (audioInput != null && !audioInput.isEmpty()) {
            String res = ad.insert(createObject(audioInput));
            System.out.println(res);
        }
    }

    /**
     * Methode zum Loeschen eines AudioImpl-Objekts
     */
    public void deleteAudio(String location) {
        if (location != null && !location.isEmpty()) {
            if (ad.delete(location)) {
                System.out.println("Delete erfolgreich");
            } else {
                System.out.println("Delete fehlgeschlagen");
            }
        }
    }

    /**
     * Read View For the CLI with all the types of media content in the System
     */
    public void listAudio() {
        int index = 1;
        String tags = "e";
        String content = "";
        list = ad.list();
        for (MediaUploadable media : list) {
            if (!media.getTags().isEmpty()){
                tags = "i";
            }
            if(media instanceof AudioImpl){content = "Audio";}
            if (media instanceof VideoImpl) {content = "Video";}
            if (media instanceof AudioVideoImpl) content = "AudioVideo";
            System.out.println(index + "." + "Content: "+content + "\n  Tags:  "+ tags + "\n  Uploader: " + media.getUploader().getName() + "\n  Address: "+media.getAddress() + "\n  AccessCount: "+media.getAccessCount());
            index++;
        }
    }

    /**
     * Methode zum Aktualisieren eines AudioImpl-Objekts
     */
    public void updateAudio(String location) {
        if (location != null && !location.isEmpty()) {
            if (ad.update(location)) {
                System.out.println("Update erfolgreich: " + ad.getObj(location).getAddress() + " Zugriffe: " +ad.getObj(location).getAccessCount());
            }
        } else {
            System.out.println("Update fehlgeschlagen");
        }
    }

    public static void main(String[] args) {
        Command cmd = new Command();
        Collection<Tag> t =cmd.parseTags("Animal,Review,Music,News");
        for (Tag tt : t) {
            System.out.println(tt);
        }
    }
}

