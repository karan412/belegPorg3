package ObserverPattern;

import domainLogic.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CapacityObserverTest {

    private Admin admin;
    private CapacityObserver observer;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        admin = mock(Admin.class);
        observer = new CapacityObserver(admin);
        System.setOut(new PrintStream(outContent));
    }


    @Test
    void testUpdate_CapacityExactly90Percent() {
        when(admin.getCAPACITY()).thenReturn(900L);
        when(admin.getMAX_CAPACITY()).thenReturn(1000L);

        observer.update();

        verify(admin, times(1)).getCAPACITY();
        verify(admin, times(1)).getMAX_CAPACITY();
    }

    @Test
    void testUpdate_CapacityAbove90Percent() {
        when(admin.getCAPACITY()).thenReturn(999L);
        when(admin.getMAX_CAPACITY()).thenReturn(1000L);

        observer.update();

        verify(admin, times(1)).getCAPACITY();
        verify(admin, times(1)).getMAX_CAPACITY();
        // Verify the output
        assertTrue(outContent.toString().contains("Capacity exceeds 90%"));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

}