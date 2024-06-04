package Server.ServerProcess;

import Server.Phases.PhaseHandler;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Connection implements Runnable {

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    protected Socket clientSocket;

    private String username;
    private String token = null;

    public Connection(Socket s) {
        this.clientSocket = s;
    }


    @Override
    public void run() {
        try {
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Server Thread. Run. IO error in server thread");
        }

        try {
            //timeout
            // clientSocket.setSoTimeout(10000);
            System.out.println("Server is Connected to Client");
            boolean isConnectionAlive = true;
            while (isConnectionAlive) {
                PhaseHandler phaseHandler = PhaseHandler.getInstance();
                phaseHandler.execute(this);
                // isConnectionAlive = false;
            }

        } catch (SocketTimeoutException s) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("Server Thread. Run. IO Error/ Client terminated abruptly");
        } catch (NullPointerException e) {
            System.err.println("Server Thread. Run.Client Closed");
        } finally {
            try {
                System.out.println("Closing the connection");
                if (inputStream != null) {
                    inputStream.close();
                    System.err.println("Socket Input Stream Closed");
                }

                if (outputStream != null) {
                    outputStream.close();
                    System.err.println("Socket Out Closed");
                }
                if (clientSocket != null) {
                    clientSocket.close();
                    System.err.println("Socket Closed");
                }
            } catch (IOException ie) {
                System.err.println("Socket Close Error");
            }
        }
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}