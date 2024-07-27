package Server.Phases.Strategies;

import Server.Protocols.Protocol;
import Server.ServerProcess.Connection;
import Server.Useful.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class PhaseStrategy {

    protected Connection connection;
    protected Constants constants = Constants.getInstance();

    public PhaseStrategy(Connection connection){
        this.connection = connection;
    }

    public abstract void execute() throws IOException;
}
