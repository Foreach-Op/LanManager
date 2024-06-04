package Server.Protocols;

import Server.ServerProcess.Connection;
import Server.Useful.Request;
import Server.Useful.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class AuthenticationProtocol extends Protocol{

    public AuthenticationProtocol(Connection connection) {
        super(connection);
    }

    @Override
    public byte[] wrap(Response response) {
        // Phase->1 byte, Type->1 byte, Status->1 byte, Message Size->4 byte, Payload->n byte
        int messageLength = response.getMessage().length();
        byte[] applicationHeader = new byte[7];
        byte[] size = ByteBuffer.allocate(4).putInt(messageLength).array();
        byte[] msg = response.getMessage().getBytes();

        applicationHeader[0] = constants.AUTHENTICATION_PHASE;
        applicationHeader[1] = response.getResponseType();
        applicationHeader[2] = response.getResponseStatus();

        for (int i = 3, j = 0; i < applicationHeader.length; i++, j++) {
            applicationHeader[i] = size[j];
        }

        byte[] payload = new byte[applicationHeader.length + messageLength];
        System.arraycopy(applicationHeader, 0, payload, 0, applicationHeader.length);

        for (int i = 7, j = 0; i < payload.length; i++, j++) {
            payload[i] = msg[j];
        }
        return payload;
    }

    @Override
    public Request getByteAndUnwrap() throws IOException {
        // Phase->1 byte (Already captured), Type->1 byte, Message Size->4 byte, Payload->n byte
        byte[] header = new byte[5];
        int err = input.read(header, 0, header.length);
        if (err == -1) throw new EOFException();

        byte type = header[0];

        byte[] size = new byte[4];
        for (int i = 0, j = 1; i < size.length; i++, j++) {
            size[i] = header[j];
        }
        int messageLength = ByteBuffer.wrap(size).getInt();
        byte[] requestMessage = new byte[messageLength];
        err = input.read(requestMessage, 0, messageLength);
        if (err == -1) throw new EOFException();
        String msg = new String(requestMessage, StandardCharsets.UTF_8);

        return new Request.RequestBuilder(constants.AUTHENTICATION_PHASE, type).setMessage(msg).build();
    }

    @Override
    public boolean checkToken(String token) {
        return true;
    }
}
