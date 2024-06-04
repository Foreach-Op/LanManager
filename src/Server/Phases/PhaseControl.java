package Server.Phases;

import Server.Useful.Constants;
import Server.Useful.PhaseEnum;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class PhaseControl {
    public PhaseEnum checkPhase(DataInputStream input) throws IOException {
        byte[] phase = new byte[1];
        int err = input.read(phase, 0, phase.length);
        if (err == -1) throw new EOFException();

        byte currentPhase = phase[0];
        return controlPhaseAndType(currentPhase);
    }

    private PhaseEnum controlPhaseAndType(byte currentPhase) {
        PhaseEnum programPhase = PhaseEnum.Authentication;
        Constants constants = Constants.getInstance();
        if (currentPhase == constants.AUTHENTICATION_PHASE) {
            programPhase = PhaseEnum.Authentication;
        } else if(currentPhase == constants.SIGNUP_PHASE){
            programPhase = PhaseEnum.Signup;
        } else if(currentPhase == constants.QUERYING_PHASE){
            programPhase = PhaseEnum.Querying;
        }
        return programPhase;
    }
}
