import tcp.TCPClient;

import java.util.Scanner;

public class Client {

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println(":c Wechsel in den Einfügemodus");
        System.out.println(":d Wechsel in den Löschmodus");
        System.out.println(":r Wechsel in den Anzeigemodus");
        System.out.println(":u Wechsel in den Änderungsmodus");
        System.out.println(":p Wechsel in den Persistenzmodus");
        System.out.println(":q Exit");
    }

        public static void main(String[] args) {
            TCPClient client = new TCPClient(9000);
            Scanner scanner = new Scanner(System.in);
            String res = "";
            boolean exit = false;

            while (!exit) {
                displayMenu();
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case ":c":
                        res = client.sendEvent(":c Media");
                        break;
                    case ":d":
                        System.out.print("Enter media to delete: ");
                        String deleteMedia = scanner.nextLine().trim();
                        res = client.sendEvent(":d "+deleteMedia);
                        break;
                    case ":r":
                        res = client.sendEvent(":r");
                        break;
                    case ":u":
                        System.out.print("Enter media to update: ");
                        String updateMedia = scanner.nextLine().trim();
                        res = client.sendEvent(":u "+updateMedia);
                        break;
                    case ":p":
                        res = client.sendEvent(":p");
                        break;
                    case ":q":
                        res = client.sendEvent(":q"); // Inform server to close connection
                        exit = true;
                        break;
                    default:
                        System.out.println("Unknown command. Please try again.");
                        continue;
                }
                System.out.println(res);
            }
    }
}

