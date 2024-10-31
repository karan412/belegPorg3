package fileSystem;

import domainLogic.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileSystemTest {

    private FileSystem fileSystem;
    private Admin admin;
    private File stateFile;

    @BeforeEach
    void setUp() {

        // Initialize the file "state.jos"
        stateFile = new File("state.jos");

        // Initialize the Admin object
        admin = new Admin(1000);

        // Initialize FileSystem with the Admin object
        fileSystem = new FileSystem(admin);
    }

    @AfterEach
    void tearDown() {
        if (stateFile.exists()) {
            stateFile.delete();
        }
    }

    @Test
    void saveState() {
        // Test saving the state to the file "state.jos"
        fileSystem.saveState(stateFile.getAbsolutePath());

        // Assert that the file was created and has content
        assertTrue(stateFile.exists());
        assertTrue(stateFile.length() > 0);
    }

    @Test
    void loadState() {
        // First save the state to the file "state.jos"
        fileSystem.saveState(stateFile.getAbsolutePath());

        // Now load the state from the file
        Admin loadedAdmin = fileSystem.loadState(stateFile.getAbsolutePath());

        // Check if the loaded admin is not null and equal to the original one
        assertNotNull(loadedAdmin);
    }

    @Test
    void loadState_FileNotFound() {
        File nonExistentFile = new File("non_existent_file.jos");
        Admin loadedAdmin = fileSystem.loadState(nonExistentFile.getAbsolutePath());

        // Assert that null is returned when the file is not found
        assertNull(loadedAdmin, "The loaded admin should be null when the file is not found.");
    }
}