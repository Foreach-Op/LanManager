package Client.ClientProcess;

import Client.DataTransform.DataClient;
import Client.Phases.PhaseHandler;
import Client.Protocols.AuthenticationProtocol;
import Client.Protocols.QueryingProtocol;
import Client.Useful.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Connection{
    private final Socket clientSocket;

    protected DataInputStream inputStream;
    protected DataOutputStream outputStream;

    private String token;

    public Connection(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            System.out.println("Client is connected to the Server");

            this.inputStream = new DataInputStream(clientSocket.getInputStream());
            this.outputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            boolean isConnectionAlive = true;
            while (isConnectionAlive) {
                PhaseHandler phaseHandler = PhaseHandler.getInstance();
                phaseHandler.execute(this, PhaseEnum.Authentication);
                if(token!=null)
                    isConnectionAlive = false;
            }
        } catch(SocketException e){
            System.err.println("Connection is timeout");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void closeEverything() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }


    public DataInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
