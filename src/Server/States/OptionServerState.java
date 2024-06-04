package Server.States;

public class OptionServerState implements ServerState {
    @Override
    public void nextState(Client client) {

    }

    @Override
    public void previousState(Client client) {
        client.setState(new AuthenticationServerState());
    }

    @Override
    public void printState() {

    }
}
