package Client.Phases.Strategies;

import Client.Phases.PhaseControl;
import Client.Protocols.Protocol;
import Client.Protocols.ProtocolFactory;
import Client.ClientProcess.Connection;
import Client.Useful.PhaseEnum;

import java.io.IOException;

public class PhaseStrategyFactory {

    public static PhaseStrategy getPhaseStrategy(Connection connection, PhaseEnum requestedPhase) throws IOException {
        // PhaseControl phaseControl = new PhaseControl();
        // PhaseEnum phaseEnum = phaseControl.checkPhase(connection.getInputStream());
        Protocol protocol = ProtocolFactory.getProtocol(connection, requestedPhase);
        if(requestedPhase == PhaseEnum.Authentication)
            return new LogInPhaseStrategy(protocol, connection);
        else if(requestedPhase == PhaseEnum.Signup)
            return new SignUpPhaseStrategy(protocol, connection);
        else if(requestedPhase == PhaseEnum.Querying)
            return new QueryingPhaseStrategy(protocol, connection);
        return new LogInPhaseStrategy(protocol, connection);
    }
}
