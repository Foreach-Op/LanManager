package Server.Phases.Strategies;

import Server.Protocols.Protocol;
import Server.Protocols.QueryingProtocol;
import Server.ServerProcess.Connection;
import Server.Useful.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdPhaseStrategy extends PhaseStrategy{

    private final Protocol protocol = new QueryingProtocol(connection);

    public CmdPhaseStrategy(Connection connection) {
        super(connection);
    }

    @Override
    public void execute() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();

        //processBuilder.command("ping", "-c", "3", "google.com");
        processBuilder.command("dir");

        Process process = processBuilder.start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        StringBuilder output = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null){
            System.out.println(line);
            output.append(line);
        }

        protocol.sendResponse(new Response.ResponseBuilder(constants.CMD_PHASE, constants.CMD_INIT, constants.RESPONSE_STATUS_SUCCESS).setMessage(output.toString()).build());

        try {
            int exitCode = process.waitFor();
            System.out.println(exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
