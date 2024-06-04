package Server.Protocols;


import Server.ServerProcess.Connection;
import Server.Useful.Constants;
import Server.Useful.Request;
import Server.Useful.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public abstract class Protocol {

    protected Connection connection;
    protected DataInputStream input;
    protected DataOutputStream output;
    protected Constants constants = Constants.getInstance();

    public Protocol(Connection connection) {
        this.connection = connection;
        this.input = connection.getInputStream();
        this.output = connection.getOutputStream();
    }

    public final void sendResponse(Response response) throws IOException {
        byte[] payload = wrap(response);
        output.write(payload,0, payload.length);
    }

    public final Request getRequest() throws Exception {
        Request input = getByteAndUnwrap();
        boolean isAuthorized = checkToken(input.getToken());
        if (isAuthorized)
            return input;
        else throw new Exception("Unauthorized Request");
    }

    protected abstract byte[] wrap(Response request);
    protected abstract Request getByteAndUnwrap() throws IOException;
    protected abstract boolean checkToken(String token);
}
