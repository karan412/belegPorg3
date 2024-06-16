package fileSystem;

import domainLogic.Admin;

import java.io.*;

/**
 * This class is responsible for saving and loading the state of the Admin object.
 */
public class FileSystem implements Serializable {

    /**
     * The Admin object to save and load.
     */
    private final Admin admin;

    /**
     * Constructor.
     * @param admin The Admin object to save and load.
     */
    public FileSystem(Admin admin) {
        this.admin = admin;
    }

    /**
     * Saves the state of the Admin object to a file.
     * @param filePath The path of the file to save the state to.
     */
    public void saveState(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(admin);
            System.out.println("State saved successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save state: " + e.getMessage());
        }
    }

    /**
     * Loads the state of the Admin object from a file.
     * @param filePath The path of the file to load the state from.
     * @return The loaded Admin object.
     */
    public Admin loadState(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Admin loadedAdmin = (Admin) ois.readObject();
            System.out.println("State loaded successfully from " + filePath);
            return loadedAdmin;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load state: " + e.getMessage());
            return null;
        }
    }
}
