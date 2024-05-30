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
     * List of AudioImpl objects
     */
    private final List<MediaUploadable> list = new ArrayList<>();

    /**
     * List Size
     */
    long SIZE_CAP = 100000L;

    /**
     * List of UploaderImpl objects
     */
    private final List<UploaderImpl> uploaderList = new ArrayList<>();

    /**
     * HashMap to map UploaderImpl objects to AudioImpl objects
     */
    private final HashMap<UploaderImpl, MediaUploadable> UploaderObjMap = new HashMap<>();

    /**
     * Boolean to check if the list is full
     */
    private boolean full = false;

    /**
     * Counter for generating address
     */
    private int counter = 0;

    /**
     * Inserts an AudioImpl object into the list
     *
     * @param media MediaImpl Object
     * @return String that indicates the success or failure of the insertion
     */
    public String insert(MediaUploadable media) {
        if (media == null) return "Insert fehlgeschlagen - Media ist null";
        else if (isFull()) {
            return "Insert fehlgeschlagen - Liste ist voll";
        } else {
            list.add(media);
            return "Insert erfolgreich";
        }
    }

    /**
     * Deletes an AudioImpl object from the list
     *
     * @param location Path of the Audio
     * @return boolean that indicates the success or failure of the deletion
     */
    public boolean delete(String location) {

        if (location == null) return false;
        else {
            for (MediaUploadable audio : list) {
                if (audio.getAddress().equals(location)) {
                    return list.remove(audio);
                }
            }
        }
        return false;
    }

    /**
     * Returns a list of AudioImpl objects
     *
     * @return List of AudioImpl objects
     */
    public List<MediaUploadable> list() {
        if (list.isEmpty()) return new ArrayList<>();
        else {
            return new ArrayList<>(list);
        }
    }

    /**
     * Updates the access count of the Media object
     *
     * @param location Path of the Audio
     * @return boolean that indicates the success or failure of the update
     */
    public boolean update(String location) {
        if (location == null) return false;
        for (MediaUploadable a : list) {
            if (a.getAddress().equals(location)) {
                a.setAccessCount(a.getAccessCount() + 1);
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
    private boolean isFull() {
        long cap = 0;
        for (MediaUploadable media : list) {
            cap += media.getSize();
            if (cap >= SIZE_CAP) {
                full = true;
                break;
            }
        }
        return full;
    }

    /**
     * Method to find an AudioObject by address
     *
     * @return int Groesse der Liste
     */
    public MediaUploadable getObj(String location) {
        if (location == null) return null;
        for (MediaUploadable a : list) {
            if (a.getAddress().equals(location)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Inserts an Uploader in the HashMap
     *
     * @param name Name des Uploaders
     * @return String, der den Erfolg oder Misserfolg des Einfuegens anzeigt
     */
    public String insertUploader(String name,AudioImpl audio) {
        if (name == null) return "Insert fehlgeschlagen - Uploader ist null";
        else {
            for(UploaderImpl u : UploaderObjMap.keySet()) {
                if(u.getName().equals(name)) {
                    return "Insert fehlgeschlagen - Uploader existiert bereits";
                }
            }
            UploaderImpl uploader = new UploaderImpl(name);
            UploaderObjMap.put(uploader, audio);
            return "Insert erfolgreich";
        }
    }

    /**
     * Prints the name of the Uploader and the Audio object
     *
     * @return boolean, der den Erfolg oder Misserfolg des Loeschens anzeigt
     */
    public String getUploader() {
        String res = null;
        for (UploaderImpl u : UploaderObjMap.keySet()) {
            res = "Uploader: " + u.getName() + " Audio: " + UploaderObjMap.get(u).getAddress();
        }
        return res;
    }

    /**
     * Deletes an Uploader from the HashMap
     *
     * @param name Name des Uploaders
     * @return boolean, der den Erfolg oder Misserfolg des Loeschens anzeigt
     */
    public boolean deleteUploader(String name) {
        if (name == null) return false;
        for (UploaderImpl u : UploaderObjMap.keySet()) {
            if (u.getName().equals(name)) {
                UploaderObjMap.remove(u);
                return true;
            }
        }
        return false;
    }


    public String generateAddress() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDate = now.format(formatter);
        int res = counter;
        counter++;
        return "file_"+formattedDate+"_"+res;
    }

    private String checkTag(MediaUploadable media) {
        if (media.getTags().isEmpty()) {
            return "i";
        } else {
            return "e";
        }
    }
}
