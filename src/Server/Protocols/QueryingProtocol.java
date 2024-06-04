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

public class QueryingProtocol extends Protocol{

    public QueryingProtocol(Connection connection){
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
        // Phase->1 byte (Already captured), Type->1 byte, Token->12 byte, Message Size->4 byte, Payload->n byte
        byte[] header = new byte[17];
        int error = input.read(header, 0, header.length);
        if (error == -1) throw new EOFException();

        byte type = header[0];

        byte[] requestToken = new byte[12];
        for (int i = 0, j = 1; i < requestToken.length; i++, j++) {
            requestToken[i] = header[j];
        }
        String token = new String(requestToken, StandardCharsets.UTF_8);

        byte[] messageSize = new byte[4];
        for (int i = 0, j = 13; i < messageSize.length; i++, j++) {
            messageSize[i] = header[j];
        }
        int messageLength = ByteBuffer.wrap(messageSize).getInt();
        byte[] requestMessage = new byte[messageLength];
        error = input.read(requestMessage, 0, messageLength);
        if (error == -1) throw new EOFException();
        String message = new String(requestMessage, StandardCharsets.UTF_8);

        return new Request.RequestBuilder(constants.QUERYING_PHASE, type).setToken(token).setMessage(message).build();
    }

    @Override
    public boolean checkToken(String token) {
        return connection.getToken().equals(token);
    }
}
