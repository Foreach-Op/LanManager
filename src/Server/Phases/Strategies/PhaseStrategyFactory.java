package Server.Phases.Strategies;

import Server.Phases.PhaseControl;
import Server.Protocols.Protocol;
import Server.Protocols.ProtocolFactory;
import Server.ServerProcess.Connection;
import Server.Useful.PhaseEnum;

import java.io.IOException;

public class PhaseStrategyFactory {

    public static PhaseStrategy getPhaseStrategy(Connection connection) throws IOException {
        PhaseControl phaseControl = new PhaseControl();
        PhaseEnum phaseEnum = phaseControl.checkPhase(connection.getInputStream());
        Protocol protocol = ProtocolFactory.getProtocol(phaseEnum, connection);
        if(phaseEnum == PhaseEnum.Authentication)
            return new LogInPhaseStrategy(protocol, connection);
        else if(phaseEnum == PhaseEnum.Querying)
            return new QueryingPhaseStrategy(protocol, connection);
        return new LogInPhaseStrategy(protocol, connection);
    }
}
