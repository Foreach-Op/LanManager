package Client.Useful;

public class Constants {
    private static Constants instance;

    private Constants(){}

    public static Constants getInstance(){
        if (instance == null)
            instance = new Constants();
        return instance;
    }

    public byte AUTHENTICATION_PHASE = 0x00;
    public byte QUERYING_PHASE = 0x01;

    public byte AUTH_LOGIN = 0x00;
    public byte AUTH_USERNAME = 0x01;
    public byte AUTH_PASSWORD = 0x02;
    public byte AUTH_SUCCESS = 0x03;
    public byte AUTH_FAIL = 0x04;

    public byte RESPONSE_STATUS_SUCCESS = 0x00;
    public byte RESPONSE_STATUS_ERROR = 0x01;


}
