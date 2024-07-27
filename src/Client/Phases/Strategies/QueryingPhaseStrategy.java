package Client.Phases.Strategies;

import Client.Protocols.AuthenticationProtocol;
import Client.Protocols.Protocol;
import Client.ClientProcess.Connection;
import Client.Protocols.QueryingProtocol;

public class QueryingPhaseStrategy extends PhaseStrategy {

    private final Protocol protocol = new QueryingProtocol(connection);

    public QueryingPhaseStrategy(Connection connection) {
        super(connection);
    }

    @Override
    public void execute() {

    }
}
