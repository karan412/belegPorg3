package domainLogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class = 100% coverage, 100% method coverage, 100% line coverage, 100% Branch coverage
 */
class AudioVideoImplTest {

    private AudioVideoImpl audioVideo;

    @BeforeEach
    void setUp() {
        audioVideo = new AudioVideoImpl(
                "audioVideo",
                "uploaderName",
                "file_address",
                Collections.emptyList(),
                4096L,
                BigDecimal.valueOf(29.99),
                Duration.ofDays(90),
                48000,
                1080
        );
    }

    @Test
    void getSamplingRate() {
        assertEquals(48000, audioVideo.getSamplingRate());
    }

    @Test
    void getResolution() {
        assertEquals(1080, audioVideo.getResolution());
    }

    @Test
    void testToString() {
        String result = audioVideo.toString();
        assertNotNull(result);
        assertTrue(result.contains("audioVideo"));
        assertTrue(result.contains("uploaderName"));
        assertTrue(result.contains("sampling rate= 48000"));
        assertTrue(result.contains("resolution= 1080"));
    }

    @Test
    void getAddress() {
        assertEquals("file_address", audioVideo.getAddress());
    }

    @Test
    void getTags() {
        assertTrue(audioVideo.getTags().isEmpty());
    }

    @Test
    void getAccessCount() {
        assertEquals(0, audioVideo.getAccessCount());
    }

    @Test
    void getSize() {
        assertEquals(4096L, audioVideo.getSize());
    }

    @Test
    void getUploader() {
        assertEquals("uploaderName", audioVideo.getUploader().getName());
    }

    @Test
    void getAvailability() {
        Duration availability = audioVideo.getAvailability();
        assertTrue(availability.getSeconds() >= 0);
        assertTrue(availability.getSeconds() < 2, "Availability should be close to 0 right after creation");
    }


    @Test
    void getCost() {
        assertEquals(BigDecimal.valueOf(29.99), audioVideo.getCost());
    }

    @Test
    void setAccessCount() {
        audioVideo.setAccessCount(15);
        assertEquals(15, audioVideo.getAccessCount());
    }

    @Test
    void getMediaContentType() {
        assertEquals("audioVideo", audioVideo.getMediaContentType());
    }
}