package Server.Phases.Strategies;

import Server.Protocols.Protocol;
import Server.ServerProcess.Connection;
import Server.Useful.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class PhaseStrategy {

    protected Protocol protocol;
    protected Connection connection;
    protected Constants constants = Constants.getInstance();

    public PhaseStrategy(Protocol protocol, Connection connection){
        this.protocol = protocol;
        this.connection = connection;
    }

    public abstract void execute() throws IOException;
}
