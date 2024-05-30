package domainLogic;

import contract.Tag;
import contract.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uploaderManger.MediaUploadable;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminTest {

    private Admin ad;
    private AudioImpl audio;
    private AudioImpl audio2;
    private AudioImpl audio3;

    @BeforeEach
    void initTestData() {
        Uploader uploader = new UploaderImpl("UploaderName");

        // Create a sample collection of tags
        Collection<Tag> tags = Arrays.asList(Tag.Music, Tag.News);

        // Define other parameters
        String address = "example.com/audio";
        long size = 1L;
        BigDecimal cost = new BigDecimal("19.99");
        Duration availability = Duration.ofDays(30);
        int samplingRate = 44100;
        long accessCount = 123;

        // Create an instance of AudioImpl
        audio = new AudioImpl(uploader, tags, address, size, cost, availability, samplingRate, accessCount);

        audio2 = new AudioImpl();
        audio3 = new AudioImpl();


        // Initialisierung fuer Admin Objekt
        ad = new Admin();
    }

    @Test
    void insertNullObj() {

        String res = ad.insert(null);

        assertEquals("Insert fehlgeschlagen - Media ist null", res);
        System.out.println("Audio Address: " + audio.getAddress());
    }

    @Test
    void insertObj() {

        audio = mock(AudioImpl.class);
        when(audio.getAccessCount()).thenReturn(0L);
        assertEquals(0L, audio.getAccessCount());
        assertEquals("Insert erfolgreich", ad.insert(audio));
        verify(audio, times(1)).getAccessCount();
        when(audio.getAccessCount()).thenReturn(1L);

    }

    @Test
    void listSizeAfterInsert() {
        ad.insert(audio);
        ad.insert(audio2);
        ad.insert(audio3);
        assertEquals(3, ad.list().size());
    }

    @Test
    void deleteNullObj() {

        ad.insert(audio);
        int list1 = ad.list().size();
        assertFalse(ad.delete(null));

        assertEquals(list1, ad.list().size());
    }

    @Test
    void deleteInvalidLocation() {

        ad.insert(audio);
        List<MediaUploadable> list1 = ad.list();
        assertFalse(ad.delete("example.com/video"));
        assertEquals(list1.size(), ad.list().size());
    }

    @Test
    void deleteFromList() {

        String loc = "example.com/audio";
        ad.insert(audio);
        ad.insert(audio2);
        List<MediaUploadable> list1 = ad.list();
        ad.delete(loc); //ad.delete(audio.getAddress());
        assertEquals(list1.size() - 1, ad.list().size());
        assertEquals(1, ad.list().size());
    }


    @Test
    void listMethodTest() {

        assertEquals("Insert erfolgreich", ad.insert(audio));
        assertEquals("Insert erfolgreich", ad.insert(audio2));

        List<MediaUploadable> list = ad.list();

    }

    @Test
    void updateWithMockito() {

        audio = mock(AudioImpl.class);
        when(audio.getAddress()).thenReturn("example.com/audio");
        when(audio.getAccessCount()).thenReturn(0L);
        ad.insert(audio);
        assertTrue(ad.update("example.com/audio"));
        assertTrue(ad.update("example.com/audio"));
        when(audio.getAccessCount()).thenReturn(2L);
    }

    @Test
    void mockitoTest() {

        audio = mock(AudioImpl.class);
        when(audio.getAddress()).thenReturn("example.com/audio");
        ad.insert(audio);
        assertTrue(ad.update("example.com/audio"));
        verify(audio, times(1)).getAccessCount();
        verify(audio).setAccessCount(1L);
        when(audio.getAccessCount()).thenReturn(1L);
    }

}