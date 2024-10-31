package domainLogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Class = 100% coverage, 100% method coverage, 100% line coverage, 100% Branch coverage
 */
class UploaderImplTest {

    @Test
    void getName() {
        UploaderImpl uploader = new UploaderImpl("testUploader");
        assertEquals("testUploader", uploader.getName());
    }

    @Test
    void testToString() {
        UploaderImpl uploader = new UploaderImpl("testUploader");
        String result = uploader.toString();
        assertNotNull(result);
        assertTrue(result.contains("uploaderName='testUploader'"));
    }
}