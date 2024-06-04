package Server.States;

public interface ServerState {
    void nextState(Client client);
    void previousState(Client client);
    void printState();
}
