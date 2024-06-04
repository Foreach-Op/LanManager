package Client.Phases.Strategies;

import Client.ClientProcess.Connection;
import Client.Protocols.Protocol;
import Client.Useful.Request;
import Client.Useful.Response;

import java.io.*;
import java.util.Scanner;

public class SignUpPhaseStrategy extends PhaseStrategy{
    private final Scanner scanner = new Scanner(System.in);

    public SignUpPhaseStrategy(Protocol protocol, Connection connection) {
        super(protocol, connection);
    }

    @Override
    public void execute() throws IOException {
        boolean isSignedUp = false;
        sendSignUpRequest();
        while (!isSignedUp){
            try {
                Response response = protocol.getResponse();
                byte responseType = response.getResponseType();
                System.out.println(response.getMessage());
                if(responseType==constants.SIGNUP_USERNAME)
                    sendUsername();
                else if(responseType==constants.SIGNUP_PASSWORD)
                    sendPassword();
                else if(responseType==constants.SIGNUP_SUCCESS){
                    isSignedUp = true;
                }else if(responseType==constants.SIGNUP_FAIL){
                    sendSignUpRequest();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendSignUpRequest() throws IOException {
        protocol.sendRequest(new Request.RequestBuilder(constants.SIGNUP_PHASE, constants.SIGNUP_INIT).setMessage("Signup").build());
    }

    private void sendUsername() throws IOException {
        String username = scanner.nextLine();
        protocol.sendRequest(new Request.RequestBuilder(constants.SIGNUP_PHASE, constants.SIGNUP_USERNAME).setMessage(username).build());
    }

    private void sendPassword() throws IOException {
        String password = scanner.nextLine();
        protocol.sendRequest(new Request.RequestBuilder(constants.SIGNUP_PHASE, constants.SIGNUP_PASSWORD).setMessage(password).build());
    }
}
