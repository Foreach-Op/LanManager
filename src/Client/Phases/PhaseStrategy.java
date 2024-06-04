package Client.Phases;

import Client.Protocols.Protocol;
import Client.ClientProcess.Connection;
import Client.Useful.Constants;

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
