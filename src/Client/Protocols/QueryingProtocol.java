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

public class QueryingProtocol extends Protocol{

    public QueryingProtocol(Connection connection){
        super(connection);
    }

    @Override
    public byte[] wrap(Request request) {
        // Phase->1 byte, Type->1 byte, Token->12 byte, Message Size->4 byte, Payload->n byte
        int messageLength = request.getMessage().length();
        byte[] applicationHeader = new byte[18];
        byte[] size = ByteBuffer.allocate(4).putInt(messageLength).array();
        byte[] token = request.getToken().getBytes();
        byte[] msg = request.getMessage().getBytes();

        applicationHeader[0] = request.getPhase();
        applicationHeader[1] = request.getRequestType();

        for (int i = 2, j = 0; j < token.length; i++, j++) {
            applicationHeader[i] = token[j];
        }

        for (int i = 2 + token.length, j = 0; j < size.length; i++, j++) {
            applicationHeader[i] = size[j];
        }

        byte[] payload = new byte[applicationHeader.length + messageLength];
        System.arraycopy(applicationHeader, 0, payload, 0, applicationHeader.length);

        for (int i = 7, j = 0; j < msg.length; i++, j++) {
            payload[i] = msg[j];
        }
        return payload;
    }

    @Override
    public Response getByteAndUnwrap() throws IOException {
        // Phase->1 byte (Already captured), Type->1 byte, Status->1 byte, Message Size->4 byte, Payload->n byte
        byte[] header = new byte[6];
        int error = input.read(header, 0, header.length);
        if (error == -1) throw new EOFException();

        byte type = header[0];
        byte status = header[1];

        /*byte[] requestToken = new byte[12];
        for (int i = 0, j = 1; i < requestToken.length; i++, j++) {
            requestToken[i] = header[j];
        }
        String token = new String(requestToken, StandardCharsets.UTF_8);*/

        byte[] messageSize = new byte[4];
        for (int i = 0, j = 2; i < messageSize.length; i++, j++) {
            messageSize[i] = header[j];
        }
        int messageLength = ByteBuffer.wrap(messageSize).getInt();
        byte[] responseMessage = new byte[messageLength];
        error = input.read(responseMessage, 0, messageLength);
        if (error == -1) throw new EOFException();
        String message = new String(responseMessage, StandardCharsets.UTF_8);

        return new Response.ResponseBuilder(constants.QUERYING_PHASE, type, status).setMessage(message).build();
    }
}
