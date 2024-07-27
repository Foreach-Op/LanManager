package Server.Phases.Strategies;

import Server.Protocols.AuthenticationProtocol;
import Server.Protocols.Protocol;
import Server.ServerProcess.Connection;
import Server.Useful.Request;
import Server.Useful.Response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class SignUpPhaseStrategy extends PhaseStrategy{

    private final Protocol protocol = new AuthenticationProtocol(connection);

    public SignUpPhaseStrategy(Connection connection) {
        super(connection);
    }

    @Override
    public void execute() throws IOException {
        try {
            Request request = protocol.getRequest();
            System.out.println(request.getMessage());
            System.out.println(request.getRequestType());
            if(request.getRequestType()==constants.SIGNUP_INIT){
                greetUser();
            }else if(request.getRequestType()==constants.SIGNUP_USERNAME){
                connection.setUsername(request.getMessage());
                getPassword();
            }else if(request.getRequestType()==constants.SIGNUP_PASSWORD){
                String password = request.getMessage();
                saveUser(connection.getUsername(), password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void completeSignUp() throws IOException {
        protocol.sendResponse(new Response.ResponseBuilder(constants.SIGNUP_PHASE, constants.SIGNUP_SUCCESS, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage("User is saved").build());
    }

    private void rejectSignUp() throws IOException {
        protocol.sendResponse(new Response.ResponseBuilder(constants.SIGNUP_PHASE, constants.SIGNUP_FAIL, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage("Given username already exists").build());
    }

    private void greetUser() throws IOException {
        protocol.sendResponse(new Response.ResponseBuilder(constants.SIGNUP_PHASE, constants.SIGNUP_INIT, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage("Welcome to the system").build());
        getUsername();
    }

    private void getUsername() throws IOException {
        System.out.println("Username");
        protocol.sendResponse(new Response.ResponseBuilder(constants.SIGNUP_PHASE, constants.SIGNUP_USERNAME, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage("Username:").build());
    }

    private void getPassword() throws IOException {
        System.out.println("Password");
        protocol.sendResponse(new Response.ResponseBuilder(constants.SIGNUP_PHASE, constants.SIGNUP_PASSWORD, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage("Password:").build());
    }

    private void saveUser(String username, String password) throws IOException {
        boolean doesUsernameExist = controlUsernameExistence(username);
        if(doesUsernameExist){
            rejectSignUp();
        }else {
            recordUsernamePassword(username, password);
            completeSignUp();
        }
    }

    public boolean controlUsernameExistence(String username) {
        try {
            Scanner userScanner = new Scanner(new File(constants.TEXT_PATH));
            while (userScanner.hasNextLine()) {
                String line = userScanner.nextLine();
                String[] userAndPassword = line.split(" ");
                if(userAndPassword[0].equals(username)){
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized void recordUsernamePassword(String username, String password) {
        try {
            Files.write(Paths.get(constants.TEXT_PATH), String.format("%s %s\n",username, password).getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        /*try {
            BufferedWriter out = new BufferedWriter(new FileWriter(constants.TEXT_PATH));
            out.append(String.format("%s %s",username, password));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
