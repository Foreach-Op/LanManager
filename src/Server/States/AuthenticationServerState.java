package Server.States;

public class AuthenticationServerState implements ServerState {
    @Override
    public void nextState(Client client) {
        client.setState(new OptionServerState());
    }

    @Override
    public void previousState(Client client) {
        System.out.println("Root State");
    }

    @Override
    public void printState() {
        System.out.println();
    }
}
