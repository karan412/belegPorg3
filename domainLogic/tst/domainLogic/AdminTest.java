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

    private UploaderImpl uploader;
    private UploaderImpl uploader2;
    private UploaderImpl uploader3;

    @BeforeEach
    void initTestData() {
        long cap = 100000L;
        ad = new Admin(cap);
        uploader = new UploaderImpl("UploaderName");

        // Create a sample collection of tags
        Collection<Tag> tags = Arrays.asList(Tag.Music, Tag.News);

        // Define other parameters
        String address = "example.com/audio"; //ad.generateAddress();
        long size = 5000L;
        BigDecimal cost = new BigDecimal("19.99");
        Duration availability = Duration.ofDays(30);
        int samplingRate = 44100;
        long accessCount = 0;

        uploader2 = new UploaderImpl("UploaderName2");
        Collection<Tag> tags2 = Arrays.asList(Tag.Music, Tag.News);
        String address2 = "example.com/audio2"; //ad.generateAddress();
        long size2 = 1L;
        BigDecimal cost2 = new BigDecimal("19.99");
        Duration availability2 = Duration.ofDays(30);
        int samplingRate2 = 44100;
        long accessCount2 = 0;

        uploader3 = new UploaderImpl("UploaderName3");
        Collection<Tag> tags3 = Arrays.asList(Tag.Music, Tag.News);
        String address3 = "example.com/audio3"; //ad.generateAddress();
        long size3 = 1L;
        BigDecimal cost3 = new BigDecimal("19.99");
        Duration availability3 = Duration.ofDays(30);
        int samplingRate3 = 44100;
        long accessCount3 = 0;


        // Create an instance of AudioImpl
        audio = new AudioImpl(uploader, tags, address, size, cost, availability, samplingRate, accessCount);
        audio2 = new AudioImpl(uploader2, tags2, address2, size2, cost2, availability2, samplingRate2, accessCount2);
        audio3 = new AudioImpl(uploader3, tags3, address3, size3, cost3, availability3, samplingRate3, accessCount3);
    }

    @Test
    void insertNullObj() {

        String res = ad.insert(null);

        assertEquals("Insert fehlgeschlagen - Media or or Uploader's name is null", res);
        System.out.println("Audio Address: " + audio.getAddress());
    }

    @Test
    void insertObjMockito() {
        audio = mock(AudioImpl.class);
        Uploader uploader = mock(Uploader.class);
        when(audio.getUploader()).thenReturn(uploader);
        when(audio.getAccessCount()).thenReturn(0L);
        when(uploader.getName()).thenReturn("UploaderName");

        assertEquals(0L, audio.getAccessCount());
        String result = ad.insert(audio);
        assertEquals("Insert erfolgreich", result);

        verify(audio, times(1)).getAccessCount();
        when(audio.getAccessCount()).thenReturn(1L);
    }

    @Test
    void listSizeAfterInsert() {
        ad.insert(audio);
        ad.insert(audio2);
        ad.insert(audio3);
        assertTrue(ad.list().size() == 3);
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
        assertEquals(2, list.size());

    }

    @Test
    void updateWithMockito() {
        assertEquals("Insert erfolgreich",ad.insert(audio));
       // assertTrue(ad.delete("example.com/audio"));
        assertTrue(ad.update("example.com/audio"));
        audio=mock(AudioImpl.class);
        Uploader uploader = mock(Uploader.class);
        when(audio.getAddress()).thenReturn("example.com/audio");
        when(audio.getUploader()).thenReturn(uploader);
        //verify(audio, times(1)).setAccessCount(anyLong());
        when(audio.getAddress()).thenReturn("example.com/audio");

    }

}