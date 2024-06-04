package Server.Phases.Strategies;

import Server.Protocols.Protocol;
import Server.ServerProcess.Connection;

public class QueryingPhaseStrategy extends PhaseStrategy {
    public QueryingPhaseStrategy(Protocol protocol, Connection connection) {
        super(protocol, connection);
    }

    @Override
    public void execute() {

    }
}
