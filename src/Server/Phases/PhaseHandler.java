package Server.Phases;

import Server.Phases.Strategies.PhaseStrategy;
import Server.Phases.Strategies.PhaseStrategyFactory;
import Server.ServerProcess.Connection;

import java.io.IOException;

public class PhaseHandler {
    private static PhaseHandler phaseHandler;

    private PhaseHandler(){}

    public static PhaseHandler getInstance(){
        if(phaseHandler == null)
            phaseHandler = new PhaseHandler();
        return phaseHandler;
    }

    public void execute(Connection connection) throws IOException {
        PhaseStrategy phaseStrategy = PhaseStrategyFactory.getPhaseStrategy(connection);
        Phase phase = new Phase(phaseStrategy);
        phase.executePhase();
    }
}
