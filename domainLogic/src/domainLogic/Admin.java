package domainLogic;

import contract.Tag;
import interfaces.Observable;
import interfaces.Observer;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Admin Class for managing AudioImpl and UploaderImpl objects
 */

public class Admin implements Serializable, Observable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * Lock for synchronization
     */
    private final Lock adminLock = new ReentrantLock();

    /**
     * Maximum Capacity of the Admin
     */
    private long MAX_CAPACITY;
    /**
     * List of Observers
     */
    private List<Observer> obsList;
    /**
     * List Size
     */
    private long CAPACITY;
    /**
     * HashMap to map UploaderImpl objects to AudioImpl objects
     */
    private HashMap<UploaderImpl, Set<MediaContentImpl>> UploaderObjMap;
    /**
     * Counter for generating address
     */
    private int counter = 0;

    public Admin(long MAX_CAPACITY) {
        this.CAPACITY = 0;
        this.MAX_CAPACITY = MAX_CAPACITY;
        this.UploaderObjMap = new HashMap<>();
        this.obsList = new ArrayList<>();
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
     * Setter for CAPACITY
     */
    private void setCAPACITY(long CAPACITY) {
        this.CAPACITY = CAPACITY;
    }

    /**
     * Getter for MAX_CAPACITY
     *
     * @return MAX_CAPACITY
     */
    public long getMAX_CAPACITY() {
        return this.MAX_CAPACITY;
    }

    /**
     * Inserts an AudioImpl object into the list
     *
     * @param media MediaImpl Object
     * @return String that indicates the success or failure of the insertion
     */
    public String insert(MediaContentImpl media) {
        synchronized (adminLock) {
            try {
                if (media == null || media.getUploader().getName() == null) {
                    return "Insert fehlgeschlagen - Media or Uploader's name is null";
                }
                if (CAPACITY + media.getSize() > MAX_CAPACITY) {
                    return "Insert fehlgeschlagen - Media size exceeds capacity";
                }
                boolean uploaderExists = false;
                for (UploaderImpl u : UploaderObjMap.keySet()) {
                    if (u.getName().equals(media.getUploader().getName())) {
                        UploaderObjMap.get(u).add(media);
                        setCAPACITY(CAPACITY + media.getSize());
                        observerNotify();
                        uploaderExists = true;
                        break; // Exit the loop since we've found the uploader
                    }
                }
                if (!uploaderExists) {
                    return "Uploader nicht gefunden, Erstelle Uploader um Media einzufügen";
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
            for (Map.Entry<UploaderImpl, Set<MediaContentImpl>> entry : UploaderObjMap.entrySet()) {
                Iterator<MediaContentImpl> iterator = entry.getValue().iterator();
                while (iterator.hasNext()) {
                    MediaContentImpl media = iterator.next();
                    if (media.getAddress().equals(location)) {
                        setCAPACITY(CAPACITY - media.getSize());
                        observerNotify();
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
    public List<MediaContentImpl> list() {
        synchronized (adminLock) {
            List<MediaContentImpl> list = new ArrayList<>();
            for (Map.Entry<UploaderImpl, Set<MediaContentImpl>> entry : UploaderObjMap.entrySet()) {
                list.addAll(entry.getValue());
            }
            return list;
        }
    }

    /**
     * Retrieves all producers with their media file count
     */
    public void getProducersMediaCount() {
        synchronized (adminLock) {
            for (Map.Entry<UploaderImpl, Set<MediaContentImpl>> entry : UploaderObjMap.entrySet()) {
                System.out.println("Uploader: " + entry.getKey().getName() + " - Media Count: " + entry.getValue().size());
            }
        }
    }

    /**
     * Retrieves all tags across all media
     *
     * @return Set of all unique tags
     */
    public Set<Tag> getAllTags() {
        synchronized (adminLock) {
            Set<Tag> tags = new HashSet<>();
            for (Map.Entry<UploaderImpl, Set<MediaContentImpl>> entry : UploaderObjMap.entrySet()) {
                for (MediaContentImpl media : entry.getValue()) {
                    tags.addAll(media.getTags());
                }
            }
            return tags;
        }
    }


    /**
     * Retrieves all tags that are not used by any media
     *
     * @return Set of all unused tags
     */
    public Set<Tag> getUnusedTags() {
        synchronized (adminLock) {
            Set<Tag> unUsedTags = new HashSet<>();
            for (Tag t : Tag.values()) {
                if (!getAllTags().contains(t)) {
                    unUsedTags.add(t);
                }
            }
            return unUsedTags;
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
            for (Map.Entry<UploaderImpl, Set<MediaContentImpl>> entry : UploaderObjMap.entrySet()) {
                for (MediaContentImpl media : entry.getValue()) {
                    if (media.getAddress().equals(location)) {
                        media.setAccessCount(media.getAccessCount() + 1);
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
            if (uploader == null || uploader.getName() == null) return false;
            if (UploaderObjMap == null) {
                UploaderObjMap = new HashMap<>();
            }
            // Check if Uploader already exists
            for (UploaderImpl u : UploaderObjMap.keySet()) {
                if (u.getName().equals(uploader.getName())) {
                    return false;
                }
            }
            UploaderObjMap.put(uploader, new HashSet<>());
            return true;
        }
    }

    /**
     * Deletes an Uploader from the HashMap including Media Files
     *
     * @param name Name of the Uploader
     * @return boolean that indicates the success or failure of the deletion
     */
    public boolean deleteUploader(String name) {
        synchronized (adminLock) {
            if (name == null || UploaderObjMap == null) return false;
            Iterator<Map.Entry<UploaderImpl, Set<MediaContentImpl>>> iterator = UploaderObjMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<UploaderImpl, Set<MediaContentImpl>> entry = iterator.next();
                if (entry.getKey().getName().equals(name)) {
                    // Update CAPACITY
                    for (MediaContentImpl media : entry.getValue()) {
                        setCAPACITY(CAPACITY - media.getSize());
                    }
                    iterator.remove();
                    observerNotify();
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Method to generate an address for the Media object
     *
     * @return String that contains the address
     */
    public String generateAddress() {
        synchronized (adminLock) {
            int res = counter;
            counter++;
            return "file_" + res;
        }
    }

    /**
     * Filter Media by type
     *
     * @param type String Type of Media
     * @return List of MediaContentImpl objects
     */
    public List<MediaContentImpl> filterMedia(String type) {
        synchronized (adminLock) {
            List<MediaContentImpl> list = new ArrayList<>();
            for (Map.Entry<UploaderImpl, Set<MediaContentImpl>> entry : UploaderObjMap.entrySet()) {
                for (MediaContentImpl media : entry.getValue()) {
                    if (media.getMediaContentType().equals(type)) {
                        list.add(media);
                    }
                }
            }
            return list;
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
            this.obsList.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        synchronized (adminLock) {
            this.obsList.remove(observer);
        }
    }

    @Override
    public void observerNotify() {
        synchronized (adminLock) {
            for (Observer observer : obsList) {
                observer.update();
            }
        }
    }
}