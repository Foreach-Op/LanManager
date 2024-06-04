package Client.Protocols;

import Client.ClientProcess.Connection;
import Client.Useful.Request;
import Client.Useful.Response;

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
    public byte[] wrap(Request request) {
        // Phase->1 byte, Type->1 byte, Message Size->4 byte, Payload->n byte
        int messageLength = request.getMessage().length();
        byte[] header = new byte[6];
        byte[] size = ByteBuffer.allocate(4).putInt(messageLength).array();
        byte[] msg = request.getMessage().getBytes();

        header[0] = constants.AUTHENTICATION_PHASE;
        header[1] = request.getRequestType();

        for (int i = 2, j = 0; i < header.length; i++, j++) {
            header[i] = size[j];
        }

        byte[] payload = new byte[header.length + messageLength];
        System.arraycopy(header, 0, payload, 0, header.length);

        for (int i = 6, j = 0; i < payload.length; i++, j++) {
            payload[i] = msg[j];
        }
        return payload;
    }

    @Override
    public Response getByteAndUnwrap() throws IOException {
        // Phase->1 byte (Already captured), Type->1 byte, Status->1 byte, Message Size->4 byte, Payload->n byte
        byte[] header = new byte[7];
        int err = input.read(header, 0, header.length);
        if (err == -1) throw new EOFException();

        byte phase = header[0];
        byte type = header[1];
        byte status = header[2];

        byte[] size = new byte[4];
        for (int i = 0, j = 3; i < size.length; i++, j++) {
            size[i] = header[j];
        }
        int messageLength = ByteBuffer.wrap(size).getInt();
        byte[] requestMessage = new byte[messageLength];
        err = input.read(requestMessage, 0, messageLength);
        if (err == -1) throw new EOFException();
        String msg = new String(requestMessage, StandardCharsets.UTF_8);
        return new Response.ResponseBuilder(constants.AUTHENTICATION_PHASE, type, status).setMessage(msg).build();
    }
}
