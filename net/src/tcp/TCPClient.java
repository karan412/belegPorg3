package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class TCPClient {
    private Socket socket;
    private InputStream inpStr;
    private ObjectOutputStream outStr;

    public TCPClient(int port) {
        try {
            socket = new Socket(InetAddress.getLocalHost(), port);
            inpStr = socket.getInputStream();
            outStr = new ObjectOutputStream(socket.getOutputStream());
        } catch (ConnectException e) {
            System.out.println("Server not running");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String sendEvent(String request) {
        try {
            if (!socket.isConnected()) {
                throw new IOException("Socket not connected");
            }
            outStr.writeObject(request);
            outStr.flush();
            byte[] responseBuffer = new byte[1024];
            inpStr.read(responseBuffer);
            return new String(responseBuffer, StandardCharsets.UTF_8).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
