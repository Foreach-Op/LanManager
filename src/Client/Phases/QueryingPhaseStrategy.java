package Client.Phases;

import Client.Protocols.Protocol;
import Client.ClientProcess.Connection;

public class QueryingPhaseStrategy extends PhaseStrategy {
    public QueryingPhaseStrategy(Protocol protocol, Connection connection) {
        super(protocol, connection);
    }

    @Override
    public void execute() {

    }
}
