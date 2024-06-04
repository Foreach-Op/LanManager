package Server.Phases.Strategies;

import Server.Protocols.Protocol;
import Server.ServerProcess.Connection;
import Server.Useful.Request;
import Server.Useful.Response;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class SignUpPhaseStrategy extends PhaseStrategy{

    public SignUpPhaseStrategy(Protocol protocol, Connection connection) {
        super(protocol, connection);
    }

    @Override
    public void execute() throws IOException {
        try {
            Request request = protocol.getRequest();
            System.out.println(request.getMessage());
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
        protocol.sendResponse(new Response.ResponseBuilder(constants.AUTHENTICATION_PHASE, constants.AUTH_SUCCESS, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage("User is saved").build());
    }

    private void rejectSignUp() throws IOException {
        protocol.sendResponse(new Response.ResponseBuilder(constants.AUTHENTICATION_PHASE, constants.AUTH_FAIL, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage("Given username already exists").build());
    }

    private void greetUser() throws IOException {
        protocol.sendResponse(new Response.ResponseBuilder(constants.AUTHENTICATION_PHASE, constants.AUTH_INIT, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage("Welcome to the system").build());
        getUsername();
    }

    private void getUsername() throws IOException {
        System.out.println("Username");
        protocol.sendResponse(new Response.ResponseBuilder(constants.AUTHENTICATION_PHASE, constants.AUTH_USERNAME, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage("Username:").build());
    }

    private void getPassword() throws IOException {
        System.out.println("Password");
        protocol.sendResponse(new Response.ResponseBuilder(constants.AUTHENTICATION_PHASE, constants.AUTH_PASSWORD, constants.RESPONSE_STATUS_SUCCESS)
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

    public boolean recordUsernamePassword(String username, String password) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(constants.TEXT_PATH));
            out.write(String.format("%s %s",username, password));
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
