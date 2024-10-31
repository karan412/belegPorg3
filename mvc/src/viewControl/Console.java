package viewControl;

import util.Command;

import java.util.Scanner;

/**
 * Console class for the application
 */
public class Console {
    /**
     * Command instance
     */
    private final Command cmd;
    /**
     * Scanner for user input
     */
    private final Scanner scanner;

    /**
     * Mode of the console
     */
    private Mode mode;

    /**
     * Constructor
     *
     * @param cmd Command
     */
    public Console(Command cmd) {
        this.cmd = cmd;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display the menu
     */
    private void displayMenu() {
        System.out.println("\nMediaManager");
        System.out.println("Verfügbare Befehle:");
        System.out.println(":c - Einfügemodus");
        System.out.println(":d - Löschmodus");
        System.out.println(":r - Anzeigemodus");
        System.out.println(":u - Änderungsmodus");
        System.out.println(":p - Persistenzmodus");
        System.out.println(":q - Beenden");
    }

    /**
     * Execute the application
     */
    public void execute() {
        boolean running = true;

        displayMenu();
        while (running) {
            String input = scanner.nextLine().trim();

            if (input.startsWith(":")) {
                switch (input) {
                    case ":c" -> mode = Mode.INSERT;
                    case ":d" -> mode = Mode.DELETE;
                    case ":r" -> mode = Mode.READ;
                    case ":u" -> mode = Mode.UPDATE;
                    case ":p" -> mode = Mode.PERSISTENCE;
                    case ":q" -> running = false;
                }
            } else {
                handleModeSpecificCommand(input);
            }
        }
    }

    /**
     * Handle mode-specific commands
     *
     * @param input user input
     */
    private void handleModeSpecificCommand(String input) {
        try {
            switch (mode) {
                case INSERT -> handleInsertMode(input);
                case DELETE -> handleDeleteMode(input);
                case READ -> handleReadMode(input);
                case UPDATE -> handleUpdateMode(input);
                case PERSISTENCE -> handlePersistenceMode(input);
            }
        } catch (NullPointerException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    /**
     * Handle commands in INSERT mode
     */
    private void handleInsertMode(String input) {
        if (input.isEmpty()) return;
        String[] tokens = input.split("\\s+");
        // Check if this is a media insertion or uploader insertion
        if (tokens[0].equals("Audio") || tokens[0].equals("Video") || tokens[0].equals("AudioVideo")) {
            cmd.insertMedia(input);
        } else {
            cmd.insertUploader(input);
        }
    }

    /**
     * Handle commands in DELETE mode
     */
    private void handleDeleteMode(String input) {
       if (input.startsWith("file")) {
            cmd.deleteMedia(input);
        } else {
            cmd.deleteUploader(input);
        }
    }

    /**
     * Handle commands in READ mode
     */
    private void handleReadMode(String input) {
        switch (input) {
            case "content":
                cmd.displayAllContent(input);
                break;
            case "content Audio", "content Video", "content AudioVideo":
                input = input.replace("content ", "");
                cmd.filterMedia(input);
                break;
            case "uploader":
                cmd.displayScreen();
                break;
            case "tag i", "tag e":
                input = input.replace("tag ", "");
                cmd.displayTags(input);
                break;
            default:
                System.out.println("Unknown mode: " + input);
        }
    }

    /**
     * Handle commands in UPDATE mode
     */
    private void handleUpdateMode(String input) {
        if (input.isEmpty()) return;
        cmd.updateMedia(input);
    }

    /**
     * Handle commands in PERSISTENCE mode
     */
    private void handlePersistenceMode(String input) {
        if (input.isEmpty()) return;

        String[] tokens = input.split("\\s+");
        if (tokens.length != 2) return;

        String command = tokens[0];
        String tech = tokens[1];

        if (!tech.equals("jos") && !tech.equals("jbp")) return;

        switch (command) {
            // save jos OR load jos works
            case "save" -> {
                cmd.saveState(tech);
                System.out.println("State saved successfully");
            }
            case "load" -> {
                cmd.loadState(tech);
                System.out.println("State loaded successfully");
            }
        }
    }

    /**
     * Modes of the console
     */
    private enum Mode {
        INSERT, DELETE, READ, UPDATE, PERSISTENCE
    }
}