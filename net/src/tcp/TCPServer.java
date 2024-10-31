package tcp;

import contract.Tag;
import domainLogic.Admin;
import domainLogic.AudioImpl;
import domainLogic.MediaContentImpl;
import domainLogic.UploaderImpl;

import java.io.*;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;


public class TCPServer extends Thread {
    private final Admin ad;
    private final int port;

    public TCPServer(Admin mediaManager, int port) {
        this.ad = mediaManager;
        this.port = port;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCPServer started on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                handleClient(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             OutputStream out = socket.getOutputStream()) {
            String req;
            // Continue to read from the stream until the client sends ":q"
            while ((req = (String) in.readObject()) != null) {
                // Log the received request
                System.out.println("Received from Client: " + req);
                if (":q".equals(req.trim())) {
                    break;
                }
                String reply = handleEvent(req);
                byte[] replyBytes = reply.getBytes();
                out.write(replyBytes);
                out.flush();
            }
        } catch (EOFException e) {
            System.out.println("Connection Closed.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String handleEvent(String request) {
        String[] parts = request.split(" ", 2);
        String command = parts[0];
        String argument;
        if (parts.length > 1) argument = parts[1];
        else argument = "";
        switch (command) {
            case ":c":
                boolean success = insert(argument);
                if (success) {
                    return "Inserted " + argument;
                }
                return "Failed to insert " + argument;
            case ":d":
                if (ad.delete(argument)) {
                    return "Deleted " + argument;
                }
                return "Failed to delete " + argument;
            case ":r":
                 String s = getListAsString();
                 return s;
            case ":u":
                if (ad.update(argument)) {
                    return "Updated " + argument;
                }
                return "Failed to update " + argument;
            case ":q":
                System.exit(0);
            default:
                return "Unknown command: " + command;
        }
    }

    private String getListAsString() {
        StringBuilder result = new StringBuilder();
        if(!ad.list().isEmpty()) {
            for (MediaContentImpl media : ad.list()) {
                result.append(media.toString()).append("\n");
                return result.toString();
            }
        }
        return "No media available";

    }
    private boolean insert(String argument) {
        if (argument.equals("Media")) {
            Collection<Tag> tags = Arrays.asList(Tag.Music, Tag.News);
            // Inserting test data and using the same obj for cru operations
            String address = "location"; //ad.generateAddress();
            UploaderImpl up = new UploaderImpl("Hans");
            ad.insertUploader(up);
            long size = 5000L;
            BigDecimal cost = new BigDecimal("19.99");
            Duration availability = Duration.ofDays(30);
            int samplingRate = 44100;
            long accessCount = 0;
            ad.insert(new AudioImpl("Audio", up.getName(), address, tags, size, cost, availability, samplingRate));
            return true;
        }if (argument.equals("Uploader")) {
            ad.insertUploader(new UploaderImpl("Hans"));
            return true;
        }
        return false;
    }
}