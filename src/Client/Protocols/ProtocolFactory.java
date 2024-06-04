package Client.Protocols;

import Client.Useful.PhaseEnum;
import Client.Protocols.AuthenticationProtocol;
import Client.Protocols.Protocol;
import Client.Protocols.QueryingProtocol;
import Client.ClientProcess.Connection;

public class ProtocolFactory {
    public static Protocol getProtocol(Connection connection, PhaseEnum requestedPhase){
        if(requestedPhase==PhaseEnum.Authentication || requestedPhase==PhaseEnum.Signup){
            return new AuthenticationProtocol(connection);
        } else if(requestedPhase==PhaseEnum.Querying){
            return new QueryingProtocol(connection);
        }else{
            return new AuthenticationProtocol(connection);
        }
    }
}