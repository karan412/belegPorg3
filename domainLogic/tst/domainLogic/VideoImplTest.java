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
class VideoImplTest {

    private VideoImpl video;

    @BeforeEach
    void setUp() {
        video = new VideoImpl(
                "video",
                "file_address",
                "uploaderName",
                Collections.emptyList(),
                1024L,
                BigDecimal.valueOf(9.99),
                Duration.ofDays(30),
                1080
        );
    }

    @Test
    void getResolution() {
        assertEquals(1080, video.getResolution());
    }

    @Test
    void testToString() {
        String result = video.toString();
        assertNotNull(result);
        assertTrue(result.contains("video"));
        assertTrue(result.contains("uploaderName"));
        assertTrue(result.contains("resolution= 1080"));
    }

    @Test
    void getAddress() {
        assertEquals("file_address", video.getAddress());
    }

    @Test
    void getTags() {
        assertTrue(video.getTags().isEmpty());
    }

    @Test
    void getAccessCount() {
        assertEquals(0, video.getAccessCount());
    }

    @Test
    void getSize() {
        assertEquals(1024L, video.getSize());
    }

    @Test
    void getUploader() {
        assertEquals("uploaderName", video.getUploader().getName());
    }

    @Test
    void getAvailability() {
        Duration availability = video.getAvailability();
        assertTrue(availability.getSeconds() >= 0);
        assertTrue(availability.getSeconds() < 2, "Availability should be close to 0 right after creation");
    }

    @Test
    void getCost() {
        assertEquals(BigDecimal.valueOf(9.99), video.getCost());
    }

    @Test
    void setAccessCount() {
        video.setAccessCount(5);
        assertEquals(5, video.getAccessCount());
    }

    @Test
    void getMediaContentType() {
        assertEquals("video", video.getMediaContentType());
    }
}