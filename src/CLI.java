import util.Command;
import viewControl.Console;


/**
 * CLI Klasse für die Steuerung der Anwendung
 */

public class CLI {

    /**
     * Main Methode
     * @param args String[]
     */
    public static void main(String[] args) {

        Command cmd = new Command();
        Console view = new Console(cmd);
        view.execute();
    }
}
