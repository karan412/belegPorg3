import domainLogic.Admin;
import tcp.TCPServer;

public class Server {

    public static void main(String[] args) {
        Admin mediaManager = new Admin(10000L);
        int port = 9000;
        TCPServer tcpServer = new TCPServer(mediaManager, port);
        tcpServer.start();
    }

}
