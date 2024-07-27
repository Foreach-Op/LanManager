package Server.Phases.Strategies;

import Server.Protocols.AuthenticationProtocol;
import Server.Protocols.Protocol;
import Server.Protocols.QueryingProtocol;
import Server.ServerProcess.Connection;

public class QueryingPhaseStrategy extends PhaseStrategy {

    private final Protocol protocol = new QueryingProtocol(connection);

    public QueryingPhaseStrategy(Connection connection) {
        super(connection);
    }

    @Override
    public void execute() {

    }
}
