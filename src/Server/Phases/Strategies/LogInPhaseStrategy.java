package Server.Phases.Strategies;

import Server.Protocols.AuthenticationProtocol;
import Server.Protocols.Protocol;
import Server.ServerProcess.Connection;
import Server.Useful.Request;
import Server.Useful.Response;

import java.io.*;
import java.util.*;

public class LogInPhaseStrategy extends PhaseStrategy {

    private final Protocol protocol = new AuthenticationProtocol(connection);

    public LogInPhaseStrategy(Connection connection) {
        super(connection);
    }

    @Override
    public void execute() throws IOException {
        try {
            Request request = protocol.getRequest();
            System.out.println(request.getMessage());
            if(request.getRequestType()==constants.AUTH_INIT){
                greetUser();
            }else if(request.getRequestType()==constants.AUTH_USERNAME){
                connection.setUsername(request.getMessage());
                getPassword();
            }else if(request.getRequestType()==constants.AUTH_PASSWORD){
                String password = request.getMessage();
                authenticate(connection.getUsername(), password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void completeAuthentication() throws IOException {
        protocol.sendResponse(new Response.ResponseBuilder(constants.AUTHENTICATION_PHASE, constants.AUTH_SUCCESS, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage(connection.getToken()).build());
    }

    private void rejectAuthentication() throws IOException {
        protocol.sendResponse(new Response.ResponseBuilder(constants.AUTHENTICATION_PHASE, constants.AUTH_FAIL, constants.RESPONSE_STATUS_SUCCESS)
                .setMessage("Given username or password is wrong").build());
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

    private void authenticate(String username, String password) throws IOException {
        boolean isUserAuthenticated = controlUsernamePassword(username, password);
        if(isUserAuthenticated){
            String token = createToken(username);
            connection.setToken(token);
            completeAuthentication();
        }else {
            rejectAuthentication();
        }
    }

    public boolean controlUsernamePassword(String username, String password) {
        try {
            Scanner userScanner = new Scanner(new File(constants.TEXT_PATH));
            while (userScanner.hasNextLine()) {
                String line = userScanner.nextLine();
                String[] userAndPassword = line.split(" ");
                if(userAndPassword[0].equals(username)){
                    return userAndPassword[1].equals(password);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String createToken(String username) {
        Random random = new Random();
        int randNum = random.nextInt(1000000) + 1000000;
        return String.valueOf(username.hashCode() + randNum).substring(0, 6);
    }
}
