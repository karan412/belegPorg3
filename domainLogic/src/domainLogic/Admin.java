package domainLogic;

import ObserverPat.Observable;
import ObserverPat.Observer;
import uploaderManger.MediaUploadable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Admin Class for managing AudioImpl and UploaderImpl objects
 */

public class Admin implements Serializable, Observable{

    static final long serialVersionUID = 1L;
    /**
     * Lock for synchronization
     */
    private final Lock adminLock = new ReentrantLock();
    /**
     * List Size
     */
    private long CAPACITY;
    /**
     * HashMap to map UploaderImpl objects to AudioImpl objects
     */
    private HashMap<UploaderImpl, List<MediaUploadable>> UploaderObjMap = new HashMap<>();

    /**
     * Counter for generating address
     */
    private int counter = 0;


    public Admin(long CAPACITY) {
        this.CAPACITY = CAPACITY;
    }

    /**
     * Setter for CAPACITY
     */
    private void setCAPACITY(long CAPACITY) {
        this.CAPACITY = CAPACITY;
    }

    /**
     * Getter for CAPACITY
     *
     * @return CAPACITY
     */
    public long getCAPACITY() {
        return this.CAPACITY;
    }

    /**
     * List of Observers
     */
    private List<Observer> obsList = new ArrayList<>();

    /**
     * Inserts an AudioImpl object into the list
     *
     * @param media MediaImpl Object
     * @return String that indicates the success or failure of the insertion
     */
    public String insert(MediaUploadable media) {
        synchronized (adminLock) {
            try {
                if (media == null || media.getUploader() == null || media.getUploader().getName() == null) {
                    return "Insert fehlgeschlagen - Media or Uploader's name is null";
                }
                if (isFull() || media.getSize() > CAPACITY) {
                    return "Insert fehlgeschlagen - Liste ist voll";
                }
                boolean uploaderExists = false;
                for (UploaderImpl u : UploaderObjMap.keySet()) {
                    if (u.getName().equals(media.getUploader().getName())) {
                        UploaderObjMap.get(u).add(media);
                        setCAPACITY(CAPACITY - media.getSize());
                        this.observerNotify("Insert Media");
                        uploaderExists = true;
                        break; // Exit the loop since we've found the uploader
                    }
                }
                if (!uploaderExists) {
                    return "Uploader not found, Create Uploader first to insert Media";
                }

                return "Insert erfolgreich";
            } catch (NullPointerException e) {
                e.printStackTrace();
                return "Insert fehlgeschlagen - Fehler: " + e.getMessage();
            }
        }
    }

    /**
     * Deletes an AudioImpl object from the list
     *
     * @param location Path of the Audio
     * @return boolean that indicates the success or failure of the deletion
     */
    public boolean delete(String location) {
        synchronized (adminLock) {
            if (location == null) return false;
            for (Map.Entry<UploaderImpl, List<MediaUploadable>> entry : UploaderObjMap.entrySet()) {
                Iterator<MediaUploadable> iterator = entry.getValue().iterator();
                while (iterator.hasNext()) {
                    MediaUploadable media = iterator.next();
                    if (media.getAddress().equals(location)) {
                        setCAPACITY(CAPACITY + media.getSize());
                        this.observerNotify("Delete Media");
                        iterator.remove();
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Returns a list of AudioImpl objects
     *
     * @return List of AudioImpl objects
     */
    public List<MediaUploadable> list() {
        synchronized (adminLock) {
            List<MediaUploadable> list = new ArrayList<>();
            for (Map.Entry<UploaderImpl, List<MediaUploadable>> entry : UploaderObjMap.entrySet()) {
                list.addAll(entry.getValue());
            }
            return list;
        }
    }

    /**
     * Updates the access count of the Media object
     *
     * @param location Path of the Audio
     * @return boolean that indicates the success or failure of the update
     */
    public boolean update(String location) {
        synchronized (adminLock) {
            if (location == null) return false;
            for (Map.Entry<UploaderImpl, List<MediaUploadable>> entry : UploaderObjMap.entrySet()) {
                for (MediaUploadable media : entry.getValue()) {
                    if (media.getAddress().equals(location)) {
                        media.setAccessCount(media.getAccessCount() + 1);
                        this.observerNotify("Update Media");
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Checks if the list is full
     *
     * @return boolean that indicates if the list is full
     */
    private boolean isFull() {
        synchronized (adminLock) {
            long cap = 0;
            for (MediaUploadable media : list()) {
                cap += media.getSize();
            }
            return cap >= CAPACITY;
        }
    }

    /**
     * Method to find an MediaObject by address
     *
     * @param location Path of the Audio
     * @return int Groesse der Liste
     */
    private boolean getObj(String location) {
        synchronized (adminLock) {
            if (location == null) return false;
            for (Map.Entry<UploaderImpl, List<MediaUploadable>> entry : UploaderObjMap.entrySet()) {
                for (MediaUploadable media : entry.getValue()) {
                    if (media.getAddress().equals(location)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Inserts an Uploader into the HashMap
     *
     * @param uploader UploaderImpl Object
     * @return boolean, that indicates the success or failure of the insertion
     */
    public boolean insertUploader(UploaderImpl uploader) {
        synchronized (adminLock) {
            if (uploader == null) return false;
            if (UploaderObjMap.containsKey(uploader)) {
                return false;
            }
            UploaderObjMap.put(uploader, new ArrayList<>());
            this.observerNotify("Insert Uploader");
            return true;
        }
    }

    /**
     * Deletes an Uploader from the HashMap inkl Media Files
     *
     * @param name Name of the Uplaoder
     * @return boolean, that indicates the success or failure of the deletion
     */
    private boolean deleteUploader(String name) {
        synchronized (adminLock) {
            if (name == null || UploaderObjMap == null) return false;
            for (UploaderImpl u : UploaderObjMap.keySet()) {
                if (u.getName().equals(name)) {
                    UploaderObjMap.remove(u);
                    UploaderObjMap.values().removeIf(mediaList -> mediaList.removeIf(media -> media.getUploader().getName().equals(name)));
                    //this.observerNotify("Delete Uploader");
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Method to generate an address for the AudioImpl object
     *
     * @return String that contains the address
     */
    public String generateAddress() {
        synchronized (adminLock) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String formattedDate = now.format(formatter);
            int res = counter;
            counter++;
            return "file_" + formattedDate + "_" + res;
        }
    }

    /**
     * Checks for tags in the Media object
     *
     * @param media MediaUploadable Object
     * @return String that contains the tags
     */
    public String checkTag(MediaUploadable media) {
        synchronized (adminLock) {
            if (media == null || media.getTags() == null || media.getTags().isEmpty()) {
                return "No tags given";
            } else {
                {
                    String tags = media.getTags().toString();
                    return tags.substring(1, tags.length() - 1);
                }
            }
        }
    }

    /**
     * Filter Media by type
     *
     * @param type String Type of Media
     * @return List of MediaUploadable objects
     */
    public List<MediaUploadable> filterMedia(String type) {
        synchronized (adminLock) {
            if (list() == null) {
                System.out.println("No Media Objects found");
                return null;
            }
            List<MediaUploadable> list = list();
            List<MediaUploadable> res = new ArrayList<>();
            // Filter Media by type and add to res
            for (MediaUploadable media : list) {
                switch (type) {
                    case "Audio":
                        if (media instanceof AudioImpl) {
                            res.add(media);
                        }
                        break;
                    case "Video":
                        if (media instanceof VideoImpl) {
                            res.add(media);
                        }
                        break;
                    case "AudioVideo":
                        if (media instanceof AudioVideoImpl) {
                            res.add(media);
                        }
                        break;
                    default:
                        return null;
                }
            }
            return res;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Admin{" +
                "CAPACITY=" + CAPACITY +
                ", UploaderObjMap=" + UploaderObjMap +
                ", counter=" + counter +
                '}';

    }

    @Override
    public void registerObserver(Observer observer) {
        synchronized (adminLock) {
            obsList.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        synchronized (adminLock) {
            obsList.remove(observer);
        }
    }

    @Override
    public void observerNotify(String status) {
        synchronized (adminLock) {
            for (Observer observer : obsList) {
                observer.update();
            }
        }
    }
}