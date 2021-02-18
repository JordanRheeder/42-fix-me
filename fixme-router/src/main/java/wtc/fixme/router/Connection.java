package wtc.fixme.router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import wtc.fixme.core.Checksum;

public class Connection extends Thread {
    private int sourcePort;
    private int targetPort;
    private Socket socket;
    private String clientId;

    public Connection(int sourcePort, Socket socket) {
        this.sourcePort = sourcePort;
        this.targetPort = socket.getPort();
        this.socket = socket;
    }

    @Override
    public void run() {
        Utils.printOut(sourcePort, targetPort, "Connecting to client...");

        Random r = new Random();
        int id = r.nextInt((999999 - 100000) + 1) + 100000;

        clientId = Integer.toString(id);
        Utils.printOut(sourcePort, targetPort, "Assigned client ID: " + clientId);
        Router.targetMap.put(clientId, socket);

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String message = input.readLine();

                Utils.printOut(sourcePort, targetPort, "Got message: " + message);
                new MessageProcessor(sourcePort, targetPort).processMessage(message);
            }
        } catch (IOException e) {
            Utils.printErr(sourcePort, targetPort, "Error reading from socket: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Utils.printErr(sourcePort, targetPort, "Error closing socket: " + e.getMessage());
            }

            Utils.printOut(sourcePort, targetPort, "Disconnecting from client ID " + clientId);
            Router.targetMap.remove(clientId);
        }
    }
}
