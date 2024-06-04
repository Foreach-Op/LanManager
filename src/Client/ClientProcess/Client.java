package Client.ClientProcess;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final int DEFAULT_PORT_NUMBER = 8888;

    private Socket clientSocket;
    private Connection connection;

    public Client(){
        try {
            clientSocket = new Socket(DEFAULT_SERVER_ADDRESS, DEFAULT_PORT_NUMBER);
            connection = new Connection(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
