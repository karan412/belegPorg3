package domainLogic;

import uploaderManger.MediaUploadable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Admin Class for managing AudioImpl and UploaderImpl objects
 */
public class Admin {

    /**
     * List Size
     */
    private long SIZE_CAP;
    /**
     * List of AudioImpl objects
     */
    private List<MediaUploadable> list = new ArrayList<>();
    /**
     * HashMap to map UploaderImpl objects to AudioImpl objects
     */
    private HashMap<UploaderImpl, List<MediaUploadable>> UploaderObjMap = new HashMap<>();
    /**
     * Boolean to check if the list is full
     */
    private boolean full = false;
    /**
     * Counter for generating address
     */
    private int counter = 0;

    public Admin(long SIZE_CAP) {
        this.SIZE_CAP = SIZE_CAP;
    }

    private void setSIZE_CAP(long SIZE_CAP) {
        this.SIZE_CAP = SIZE_CAP;
    }

    /**
     * Inserts an AudioImpl object into the list
     *
     * @param media MediaImpl Object
     * @return String that indicates the success or failure of the insertion
     */
    public synchronized String insert(MediaUploadable media) {
        try {
            if (media == null || media.getUploader() == null || media.getUploader().getName() == null)
                return "Insert fehlgeschlagen - Media or or Uploader's name is null";
            if (isFull() || media.getSize() > SIZE_CAP) {
                return "Insert fehlgeschlagen - Liste ist voll";
            }
            boolean uploaderExists = false;
            for (UploaderImpl u : UploaderObjMap.keySet()) {
                if (u.getName().equals(media.getUploader().getName())) {
                    UploaderObjMap.get(u).add(media);
                    uploaderExists = true;
                }
                if (!uploaderExists) {
                    insertUploader(media.getUploader().getName());
                    if (u.getName().equals(media.getUploader().getName())) {
                        UploaderObjMap.get(u).add(media);
                    }
                }
            }
            list.add(media);
            setSIZE_CAP(SIZE_CAP - media.getSize());
            return "Insert erfolgreich";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "Insert fehlgeschlagen - Fehler: " + e.getMessage();
        }
    }

    /**
     * Deletes an AudioImpl object from the list
     *
     * @param location Path of the Audio
     * @return boolean that indicates the success or failure of the deletion
     */
    public synchronized boolean delete(String location) {
        if (location == null) return false;
        for (MediaUploadable media : list) {
            if (media.getAddress().equals(location)) {
                setSIZE_CAP(SIZE_CAP + media.getSize());
                list.remove(media);
                for (List<MediaUploadable> mediaList : UploaderObjMap.values()) {
                    mediaList.remove(media);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of AudioImpl objects
     *
     * @return List of AudioImpl objects
     */
    public synchronized List<MediaUploadable> list() {
        if (list.isEmpty()) return new ArrayList<>();
        else return new ArrayList<>(list);
    }

    /**
     * Updates the access count of the Media object
     *
     * @param location Path of the Audio
     * @return boolean that indicates the success or failure of the update
     */
    public boolean update(String location) {
        if (location == null) return false;
        for (MediaUploadable media : list) {
            if (media.getAddress().equals(location)) {
                media.setAccessCount(media.getAccessCount() + 1);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the list is full
     *
     * @return boolean that indicates if the list is full
     */
    private synchronized boolean isFull() {
        long cap = 0;
        for (MediaUploadable media : list) {
            cap += media.getSize();
        }
        return cap >= SIZE_CAP;
    }

    /**
     * Method to find an MediaObject by address
     *
     * @return int Groesse der Liste
     */
    public boolean getObj(String location) {
        if (location == null) return false;
        for (MediaUploadable a : list) {
            if (a.getAddress().equals(location)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inserts an Uploader in the HashMap
     *
     * @param name Name des Uploaders
     */
    public synchronized void insertUploader(String name) {
        if (name != null && UploaderObjMap != null && !UploaderObjMap.containsKey(name)) {
            UploaderImpl uploader = new UploaderImpl(name);
            UploaderObjMap.put(uploader, new ArrayList<>());
        }
    }

    /**
     * Deletes an Uploader from the HashMap
     *
     * @param name Name des Uploaders
     * @return boolean, der den Erfolg oder Misserfolg des Loeschens anzeigt
     */
    public synchronized boolean deleteUploader(String name) {
        if (name == null || UploaderObjMap == null) return false;
        for (UploaderImpl u : UploaderObjMap.keySet()) {
            if (u.getName().equals(name)) {
                UploaderObjMap.remove(u);
                UploaderObjMap.values().removeIf(mediaList -> mediaList.removeIf(media -> media.getUploader().getName().equals(name)));
                return true;
            }
        }
        return false;
    }

    /**
     * Method to generate an address for the AudioImpl object
     */
    public String generateAddress() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDate = now.format(formatter);
        int res = counter;
        counter++;
        return "file_" + formattedDate + "_" + res;
    }

    /**
     * Checks for tags in the Media object
     *
     * @param media MediaUploadable Object
     */
    private String checkTag(MediaUploadable media) {
        if (media == null || media.getTags() == null || media.getTags().isEmpty()) {
            return "No tags given";
        } else {
            {
                String tags = media.getTags().toString();
                return tags.substring(1, tags.length() - 1);
            }
        }
    }

    /**
     * Filter Media by type
     *
     * @param type String Type of Media
     */
    private void filterMedia(String type) {
        if (list == null) {
            System.out.println("No Media Objects found");
            return;
        }
        int index = 1;
        for (MediaUploadable media : list) {
            switch (type) {
                case "Audio":
                    if (media instanceof AudioImpl) {
                        System.out.println(index + "." + "Content: Audio " + "\n  Tags:  " + checkTag(media) + "\n  Uploader: " + media.getUploader().getName() + "\n  Address: " + media.getAddress() + "\n  AccessCount: " + media.getAccessCount() +
                                "\n  Size: " + media.getSize() + "\n  Availability: " + media.getAvailability() + "\n  Cost: " + media.getCost() + "\n  SamplingRate: " + ((AudioImpl) media).getSamplingRate());
                        index++;
                    } else {
                        System.out.println("No Audio Objects found");
                    }
                    break;
                case "Video":
                    if (media instanceof VideoImpl) {
                        System.out.println(index + "." + "Content: Audio " + "\n  Tags:  " + checkTag(media) + "\n  Uploader: " + media.getUploader().getName() + "\n  Address: " + media.getAddress() + "\n  AccessCount: " + media.getAccessCount() +
                                "\n  Size: " + media.getSize() + "\n  Availability: " + media.getAvailability() + "\n  Cost: " + media.getCost() + "\n  Resolution: " + ((VideoImpl) media).getResolution());
                    } else {
                        System.out.println("No Video Objects found");
                    }
                    break;
                case "AudioVideo":
                    if (media instanceof AudioVideoImpl) {
                        System.out.println(index + "." + "Content: Audio " + "\n  Tags:  " + checkTag(media) + "\n  Uploader: " + media.getUploader().getName() + "\n  Address: " + media.getAddress() + "\n  AccessCount: " + media.getAccessCount() +
                                "\n  Size: " + media.getSize() + "\n  Availability: " + media.getAvailability() + "\n  Cost: " + media.getCost() + "\n  Resolution: " + ((AudioVideoImpl) media).getResolution() + "\n  SamplingRate: " + ((AudioVideoImpl) media).getSamplingRate());
                    } else {
                        System.out.println("No AudioVideo Objects found");
                    }
                    break;
                default:
                    System.out.println("Type: " + type + " not identified [Kdnown Types: Audio, Video, AudioVideo]");
            }
        }
    }
}
