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
    private final Scanner scanner = new Scanner(System.in);

    private String token;

    public Connection(Socket clientSocket) throws IOException {
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
            while (true) {
                showOptions();
                int processToDo = scanner.nextInt();
                PhaseEnum requiredOption = getRequiredOption(processToDo);
                if(requiredOption==null){
                    System.out.println("Exiting");
                    break;
                }
                PhaseHandler phaseHandler = PhaseHandler.getInstance();
                phaseHandler.execute(this, requiredOption);
            }
        } catch(SocketException e){
            System.err.println("Connection is timeout");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeEverything();
        }
    }

    private PhaseEnum getRequiredOption(int processToDo){
        if(token==null){
            if(processToDo==1)
                return PhaseEnum.Authentication;
            else if(processToDo==2)
                return PhaseEnum.Signup;
        }
        else{
            if(processToDo==1)
                return PhaseEnum.Cmd;
        }
        return null;
    }

    private void showOptions(){
        if(token==null)
            showEntranceOptions();
        else
            showContentOptions();
        System.out.println("Chose your option:");
    }

    private void showEntranceOptions(){
        System.out.println("1-) Login\n2-) Signup");
    }

    private void showContentOptions(){
        System.out.println("1-) Cmd\n2-) ...");
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
