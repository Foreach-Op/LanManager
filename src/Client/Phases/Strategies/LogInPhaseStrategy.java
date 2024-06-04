package Client.Phases.Strategies;

import Client.Protocols.Protocol;
import Client.ClientProcess.Connection;
import Client.Useful.Request;
import Client.Useful.Response;

import java.io.IOException;
import java.util.Scanner;

public class LogInPhaseStrategy extends PhaseStrategy {

    private final Scanner scanner = new Scanner(System.in);

    public LogInPhaseStrategy(Protocol protocol, Connection connection) {
        super(protocol, connection);
    }

    @Override
    public void execute() throws IOException {
        boolean isAuthorized = false;
        sendLoginRequest();
        while (!isAuthorized){
            try {
                Response response = protocol.getResponse();
                byte responseType = response.getResponseType();
                System.out.println(response.getMessage());
                if(responseType==constants.AUTH_USERNAME)
                    sendUsername();
                else if(responseType==constants.AUTH_PASSWORD)
                    sendPassword();
                else if(responseType==constants.AUTH_SUCCESS){
                    String token = response.getMessage();
                    connection.setToken(token);
                    isAuthorized = true;
                }else if(responseType==constants.AUTH_FAIL){
                    sendLoginRequest();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendLoginRequest() throws IOException {
        protocol.sendRequest(new Request.RequestBuilder(constants.AUTHENTICATION_PHASE, constants.AUTH_INIT).setMessage("Login").build());
    }

    private void sendUsername() throws IOException {
        String username = scanner.nextLine();
        protocol.sendRequest(new Request.RequestBuilder(constants.AUTHENTICATION_PHASE, constants.AUTH_USERNAME).setMessage(username).build());
    }

    private void sendPassword() throws IOException {
        String password = scanner.nextLine();
        protocol.sendRequest(new Request.RequestBuilder(constants.AUTHENTICATION_PHASE, constants.AUTH_PASSWORD).setMessage(password).build());
    }
}
