package Server.States;

public class Client {
    private ServerState state = new AuthenticationServerState();

    public void previousState(){
        state.previousState(this);
    }

    public void nextState() {
        state.nextState(this);
    }

    public void printStatus() {
        state.printState();
    }

    public ServerState getState() {
        return state;
    }

    public void setState(ServerState state) {
        this.state = state;
    }
}
