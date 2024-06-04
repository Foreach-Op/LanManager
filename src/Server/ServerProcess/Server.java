package Server.ServerProcess;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PORT = 8888;
    private final ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Opened up a server socket on " + Inet4Address.getLocalHost());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        while (true) {
            ListenAndAccept();
        }
    }

    private void ListenAndAccept() {
        try {
            Socket clientSocket = serverSocket.accept();
            Connection connection = new Connection(clientSocket);
            Thread thread = new Thread(connection);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}