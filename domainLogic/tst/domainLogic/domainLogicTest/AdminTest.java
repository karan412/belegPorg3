package domainLogic.domainLogicTest;
import contract.Tag;
import domainLogic.Admin;
import domainLogic.MediaContentImpl;
import domainLogic.UploaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Class = 100% coverage, 89% method coverage, 88% line coverage, 83% Branch coverage
 */

class AdminTest {
    private Admin admin;
    private UploaderImpl uploader;
    private MediaContentImpl media;

    @BeforeEach
    void setUp() {
        admin = new Admin(1000L);
        uploader = mock(UploaderImpl.class);
        media = mock(MediaContentImpl.class);

        when(uploader.getName()).thenReturn("Uploader1");
        when(media.getUploader()).thenReturn(uploader);
        when(media.getSize()).thenReturn(100L);
        when(media.getAddress()).thenReturn("media1");
    }

    @Test
    void testInsertUploader() {
        assertTrue(admin.insertUploader(uploader));
        // Try to insert the same uploader again (Duplication)
        assertFalse(admin.insertUploader(uploader));
    }

    @Test
    void testInsertMedia() {
        admin.insertUploader(uploader);
        assertEquals("Insert erfolgreich", admin.insert(media));
    }

    @Test
    void testDeleteMedia() {
        admin.insertUploader(uploader);
        admin.insert(media);
        assertTrue(admin.delete("media1"));
        assertFalse(admin.delete("media2")); // Non-existent media
    }

    @Test
    void testUpdateMedia() {
        admin.insertUploader(uploader);
        admin.insert(media);
        when(media.getAccessCount()).thenReturn(0L);
        assertEquals(0L, media.getAccessCount());
        assertTrue(admin.update("media1"));
        assertFalse(admin.update("media2"));// Non-existent media
        when(media.getAccessCount()).thenReturn(1L);
        assertEquals(1L, media.getAccessCount());
    }

    @Test
    void testListMedia() {
        admin.insertUploader(uploader);
        admin.insert(media);
        Set<MediaContentImpl> mediaSet = new HashSet<>();
        mediaSet.add(media);
        assertEquals(mediaSet, new HashSet<>(admin.list()));
    }

    //Spy test
    @Test
    void testGenerateAddress() {
        Admin adminSpy = spy(new Admin(1000L));

        String address1 = adminSpy.generateAddress();
        String address2 = adminSpy.generateAddress();

        // Verify generateAddress was called twice
        verify(adminSpy, times(2)).generateAddress();

        // Verify addresses are different
        assertNotEquals(address1, address2);

        // Verify the format of the addresses
        assertTrue(address1.startsWith("file_"));
        assertTrue(address2.startsWith("file_"));
    }

    @Test
    void testCheckTag() {
        admin.insertUploader(uploader);
        admin.insert(media);
        when(media.getTags()).thenReturn(Set.of(Tag.Music, Tag.News, Tag.Animal, Tag.Review));
    }

    @Test
    void  testFilterMedia() {
        admin.insertUploader(uploader);
        admin.insert(media);
        when(media.getMediaContentType()).thenReturn("Audio");
        assertEquals(List.of(media), admin.filterMedia("Audio"));

        when(media.getMediaContentType()).thenReturn("Video");
        assertTrue(admin.filterMedia("Audio").isEmpty());
    }

    @Test
    void testDeleteUploader() {
        admin.insertUploader(uploader);
        admin.insert(media);
        assertTrue(admin.deleteUploader("Uploader1"));
        assertFalse(admin.deleteUploader("NonexistentUploader"));
        assertTrue(admin.list().isEmpty());
    }

    //Spy test
    @Test
    void testGetProducersMediaCount() {
        // Create a spy to verify the output
        Admin adminSpy = spy(admin);

        MediaContentImpl media2 = mock(MediaContentImpl.class);
        when(media2.getUploader()).thenReturn(uploader);
        when(media2.getSize()).thenReturn(100L);
        when(media2.getAddress()).thenReturn("media2");

        adminSpy.insertUploader(uploader);
        adminSpy.insert(media);
        adminSpy.insert(media2);

        // Use a PrintStream spy to verify output
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        adminSpy.getProducersMediaCount();

        // Verify that the correct output was printed
        assertTrue(outContent.toString().contains("Uploader: Uploader1 - Media Count: 2"));

        // Restore original System.out
        System.setOut(originalOut);
    }

    @Test
    void testGetAllTags() {
        Set<Tag> expectedTags = Set.of(Tag.Music, Tag.News);
        when(media.getTags()).thenReturn(expectedTags);

        admin.insertUploader(uploader);
        admin.insert(media);

        assertEquals(expectedTags, admin.getAllTags());
    }

    @Test
    void testGetUnusedTags() {
        Set<Tag> usedTags = Set.of(Tag.Music, Tag.News);
        when(media.getTags()).thenReturn(usedTags);

        admin.insertUploader(uploader);
        admin.insert(media);

        Set<Tag> unusedTags = admin.getUnusedTags();
        assertFalse(unusedTags.contains(Tag.Music));
        assertFalse(unusedTags.contains(Tag.News));
        assertTrue(unusedTags.contains(Tag.Animal));
    }

    @Test
    void testObserverMethods() {
        interfaces.Observer observer = mock(interfaces.Observer.class);

        admin.registerObserver(observer);
        admin.insertUploader(uploader);
        admin.insert(media);

        verify(observer, times(1)).update();

        admin.removeObserver(observer);
        admin.delete("media1");

        // Should still only be called once since we removed the observer
        verify(observer, times(1)).update();
    }

    // Spy behavior test
    @Test
    void testAdminListBehavior() {
        Admin adminSpy = spy(new Admin(1000L));
        UploaderImpl uploader1 = new UploaderImpl("Uploader1");
        UploaderImpl uploader2 = new UploaderImpl("Uploader2");

        MediaContentImpl media1 = mock(MediaContentImpl.class);
        when(media1.getUploader()).thenReturn(uploader1);
        when(media1.getSize()).thenReturn(100L);
        when(media1.getAddress()).thenReturn("address1");

        MediaContentImpl media2 = mock(MediaContentImpl.class);
        when(media2.getUploader()).thenReturn(uploader2);
        when(media2.getSize()).thenReturn(200L);
        when(media2.getAddress()).thenReturn("address2");

        // Insert uploaders and media
        adminSpy.insertUploader(uploader1);
        adminSpy.insertUploader(uploader2);
        adminSpy.insert(media1);
        adminSpy.insert(media2);

        // Spy on the list() method
        List<MediaContentImpl> mediaList = adminSpy.list();
        verify(adminSpy).list();

        // Verify list contents
        assertEquals(2, mediaList.size());
        assertTrue(mediaList.contains(media1));
        assertTrue(mediaList.contains(media2));

        // Verify capacity
        assertEquals(300L, adminSpy.getCAPACITY());

        // Delete one media and verify list is updated
        adminSpy.delete("address1");
        mediaList = adminSpy.list();
        assertEquals(1, mediaList.size());
        assertFalse(mediaList.contains(media1));
        assertTrue(mediaList.contains(media2));

        // Verify capacity after deletion
        assertEquals(200L, adminSpy.getCAPACITY());
    }

    @Test
    void testInsertNullUploader() {
        assertFalse(admin.insertUploader(null), "Inserting a null uploader should return false");
    }

    @Test
    void testInsertMediaExceedingCapacity() {
        admin.insertUploader(uploader);

        MediaContentImpl media = mock(MediaContentImpl.class);
        when(media.getUploader()).thenReturn(uploader);
        when(media.getSize()).thenReturn(2000L); // Size exceeds capacity

        assertEquals("Insert fehlgeschlagen - Media size exceeds capacity", admin.insert(media));
    }
}