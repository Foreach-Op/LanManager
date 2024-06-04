package Server.States;

public class CodeProcessServerState implements ServerState, LanProcess{
    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public void nextState(Client client) {

    }

    @Override
    public void previousState(Client client) {

    }

    @Override
    public void printState() {

    }
}
