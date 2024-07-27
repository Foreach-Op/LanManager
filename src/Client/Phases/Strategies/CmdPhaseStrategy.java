package Client.Phases.Strategies;

import Client.Phases.Strategies.PhaseStrategy;
import Client.Protocols.Protocol;
import Client.Protocols.QueryingProtocol;
import Client.ClientProcess.Connection;
import Client.Useful.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdPhaseStrategy extends PhaseStrategy {

    private final Protocol protocol = new QueryingProtocol(connection);

    public CmdPhaseStrategy(Connection connection) {
        super(connection);
    }

    @Override
    public void execute() throws IOException {
        protocol.sendRequest(new Request.RequestBuilder(constants.CMD_PHASE, constants.CMD_INIT).setToken(connection.getToken()).build());

    }
}
