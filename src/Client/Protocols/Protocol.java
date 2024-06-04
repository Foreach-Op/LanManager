package Client.Protocols;


import Client.ClientProcess.Connection;
import Client.Useful.Constants;
import Client.Useful.Request;
import Client.Useful.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


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

    public final void sendRequest(Request request) throws IOException {
        byte[] payload = wrap(request);
        output.write(payload,0, payload.length);
    }

    public final Response getResponse() throws Exception {
        return getByteAndUnwrap();
    }

    protected abstract byte[] wrap(Request request);
    protected abstract Response getByteAndUnwrap() throws IOException;
}
