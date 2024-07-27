package Client.Phases.Strategies;

import Client.Protocols.Protocol;
import Client.ClientProcess.Connection;
import Client.Useful.Constants;

import java.io.IOException;

public abstract class PhaseStrategy {

    protected Connection connection;
    protected Constants constants = Constants.getInstance();

    public PhaseStrategy(Connection connection){
        this.connection = connection;
    }

    public abstract void execute() throws IOException;
}
