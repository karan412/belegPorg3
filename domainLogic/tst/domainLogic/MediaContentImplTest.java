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
class MediaContentImplTest {

    private MediaContentImpl mediaContent;

    @BeforeEach
    void setUp() {
        mediaContent = new MediaContentImpl(
                "video",
                "uploaderName",
                "file_address",
                Collections.emptyList(),
                1024L,
                BigDecimal.valueOf(9.99),
                Duration.ofDays(30)
        );
    }

    @Test
    void testToString() {
        String result = mediaContent.toString();
        assertNotNull(result);
        assertTrue(result.contains("video"));
        assertTrue(result.contains("uploaderName"));
        assertTrue(result.contains("file_address"));
        assertTrue(result.contains("sizeMediaContent=1024"));
        assertTrue(result.contains("cost=9.99"));
        assertTrue(result.contains("accessCount=0"));
        assertTrue(result.contains("availability=PT"));
    }

    @Test
    void getAddress() {
        assertEquals("file_address", mediaContent.getAddress());
    }

    @Test
    void getTags() {
        assertTrue(mediaContent.getTags().isEmpty());
    }

    @Test
    void getAccessCount() {
        assertEquals(0, mediaContent.getAccessCount());
    }

    @Test
    void getSize() {
        assertEquals(1024L, mediaContent.getSize());
    }

    @Test
    void getUploader() {
        assertEquals("uploaderName", mediaContent.getUploader().getName());
    }

    @Test
    void getAvailability() {
        Duration availability = mediaContent.getAvailability();
        assertTrue(availability.getSeconds() >= 0);
        assertTrue(availability.getSeconds() < 2, "Availability should be close to 0 right after creation");
    }

    @Test
    void getCost() {
        assertEquals(BigDecimal.valueOf(9.99), mediaContent.getCost());
    }

    @Test
    void setAccessCount() {
        mediaContent.setAccessCount(5);
        assertEquals(5, mediaContent.getAccessCount());
    }

    @Test
    void getMediaContentType() {
        assertEquals("video", mediaContent.getMediaContentType());
    }
}