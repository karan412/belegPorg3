package domainLogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class = 100% coverage, 100% method coverage, 100% line coverage, 100% Branch coverage
 */
class AudioImplTest {
    private AudioImpl audio;
    @BeforeEach
    void setUp() {
        audio = new AudioImpl(
                "audio",
                "uploaderName",
                "file_address",
                Collections.emptyList(),
                2048L,
                BigDecimal.valueOf(19.99),
                Duration.ofDays(60),
                44100
        );
    }

    @Test
    void getSamplingRate() {
        assertEquals(44100, audio.getSamplingRate());
    }

    @Test
    void testToString() {
        String result = audio.toString();
        assertNotNull(result);
        assertTrue(result.contains("audio"));
        assertTrue(result.contains("uploaderName"));
        assertTrue(result.contains("sampling rate= 44100"));
    }

    @Test
    void getAddress() {
        assertEquals("file_address", audio.getAddress());
    }

    @Test
    void getTags() {
        assertTrue(audio.getTags().isEmpty());
    }

    @Test
    void getAccessCount() {
        assertEquals(0, audio.getAccessCount());
    }

    @Test
    void getSize() {
        assertEquals(2048L, audio.getSize());
    }

    @Test
    void getUploader() {
        assertEquals("uploaderName", audio.getUploader().getName());
    }

    @Test
    void getAvailability() {
        Duration availability = audio.getAvailability();
        assertTrue(availability.toDays() >= 0);
    }

    @Test
    void getCost() {
        assertEquals(BigDecimal.valueOf(19.99), audio.getCost());
    }

    @Test
    void setAccessCount() {
        audio.setAccessCount(10);
        assertEquals(10, audio.getAccessCount());
    }

    @Test
    void getMediaContentType() {
        assertEquals("audio", audio.getMediaContentType());
    }
}