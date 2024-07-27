package Server.Useful;

public class Response {
    private byte phase;
    private byte responseType;
    private byte responseStatus;
    private String message;

    private Response(Response.ResponseBuilder builder) {
        this.phase=builder.phase;
        this.responseType =builder.responseType;
        this.responseStatus =builder.responseStatus;
        this.message=builder.message;
    }

    public byte getPhase() {
        return phase;
    }

    public void setPhase(byte phase) {
        this.phase = phase;
    }

    public byte getResponseType() {
        return responseType;
    }

    public void setResponseType(byte responseType) {
        this.responseType = responseType;
    }

    public byte getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(byte responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ResponseBuilder {
        private final byte phase;
        private final byte responseType;
        private final byte responseStatus;
        private String message = "";

        public ResponseBuilder(byte phase, byte responseType, byte responseStatus) {
            this.phase = phase;
            this.responseType = responseType;
            this.responseStatus = responseStatus;
        }

        public ResponseBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Response build(){
            return new Response(this);
        }
    }
}



