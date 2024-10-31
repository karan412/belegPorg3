package viewControl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Command;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.mockito.Mockito.*;

class ConsoleTest {

    private Command mockCommand;
    private Console console;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        mockCommand = mock(Command.class);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    private void provideInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        console = new Console(mockCommand);
    }

    @Test
    void insertUploader_ShouldCallCommandInsertUploader() {
        provideInput(":c\nTestUploader\n:q\n");

        console.execute();

        verify(mockCommand).insertUploader("TestUploader");
    }

    @Test
    void displayUploader_ShouldCallCommandDisplayScreen() {
        provideInput(":r\nuploader\n:q\n");

        console.execute();

        verify(mockCommand).displayScreen();
    }

    @Test
    void unknownCommand_ShouldNotCallAnyCommandMethods() {
        provideInput(":r\nunknowncommand\n:q\n");

        console.execute();

        verifyNoInteractions(mockCommand);
    }

    @Test
    void insertMode_ThenDisplayMode_ShouldHandleBothCorrectly() {
        provideInput(":c\nTestUploader1\n:r\nuploader\n:q\n");

        console.execute();

        verify(mockCommand).insertUploader("TestUploader1");
        verify(mockCommand).displayScreen();
    }

}