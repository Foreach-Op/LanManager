package Client.Phases;

import Client.ClientProcess.Connection;
import Client.Useful.PhaseEnum;

import java.io.IOException;

public class PhaseHandler {
    private static PhaseHandler phaseHandler;

    private PhaseHandler(){}

    public static PhaseHandler getInstance(){
        if(phaseHandler == null)
            phaseHandler = new PhaseHandler();
        return phaseHandler;
    }

    public void execute(Connection connection, PhaseEnum phaseEnum) throws IOException {
        PhaseStrategy phaseStrategy = PhaseStrategyFactory.getPhaseStrategy(connection, phaseEnum);
        Phase phase = new Phase(phaseStrategy);
        phase.executePhase();
    }
}
