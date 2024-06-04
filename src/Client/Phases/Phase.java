package Client.Phases;

import Client.Phases.Strategies.PhaseStrategy;

import java.io.IOException;

public class Phase {
    private final PhaseStrategy phaseStrategy;

    public Phase(PhaseStrategy phaseStrategy){
        this.phaseStrategy = phaseStrategy;
    }

    public void executePhase() throws IOException {
        phaseStrategy.execute();
    }
}
