package viewControl;

import util.Command;

import java.util.Scanner;

/**
 * Console Klasse für die Steuerung der Anwendung
 */
public class Console {
    private Command cmd;

    /**
     * Konstruktor
     *
     * @param cmd Command
     */
    public Console(Command cmd) {
        this.cmd = cmd;
    }

    /**
     * Methode zum Ausführen der Anwendung
     */
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.println("\n Wähle Ein Option:");
                System.out.println(":c Wechsel in den Einfügemodus");
                System.out.println(":d Wechsel in den Löschmodus");
                System.out.println(":r Wechsel in den Anzeigemodus");
                System.out.println(":u Wechsel in den Änderungsmodus");
                System.out.println(":p Wechsel in den Persistenzmodus");
                System.out.println(":q Beenden");

                String input = scanner.nextLine();

                switch (input) {
                    case ":c":
                        System.out.println("bsp: [Media-Typ] [P-Name] [kommaseparierte Tags, einzelnes Komma für keine] [Größe] [Abrufkosten] [[Optionale Parameter]]");
                        String audioObj = scanner.nextLine();
                        cmd.insertAudio(audioObj);
                        break;
                    case ":d":
                        System.out.println("Geben Sie die zu löschende Adresse ein (bsp: [Adresse]):");
                        String location = scanner.nextLine();
                        cmd.deleteAudio(location);
                        break;
                    case ":r":
                        cmd.listAudio();
                        break;
                    case ":u":
                        System.out.println("Geben Sie die zu aktualisierende Adresse ein (bsp: [Adresse]):");
                        String loc = scanner.nextLine();
                        cmd.updateAudio(loc);
                        break;
                    case ":p":
                        //persist();
                        break;
                    case ":q":
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
                }
            } while (true);
        }
    }
}
