package Server.Protocols;

import Server.Useful.PhaseEnum;
import Server.ServerProcess.Connection;

public class ProtocolFactory{
    public static Protocol getProtocol(PhaseEnum phaseEnum, Connection connection){
        if(phaseEnum==PhaseEnum.Authentication || phaseEnum==PhaseEnum.Signup){
            return new AuthenticationProtocol(connection);
        } else if(phaseEnum==PhaseEnum.Querying){
            return new QueryingProtocol(connection);
        }else{
            return new AuthenticationProtocol(connection);
        }
    }
}