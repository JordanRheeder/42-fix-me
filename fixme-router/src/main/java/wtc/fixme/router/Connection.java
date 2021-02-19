package wtc.fixme.router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import wtc.fixme.core.Checksum;

public class Connection extends Thread {
    private int listenPort;
    private int targetPort;
    private Socket socket;
    private String clientId;

    public Connection(int listenPort, Socket socket) {
        this.listenPort = listenPort;
        this.targetPort = socket.getPort();
        this.socket = socket;
    }

    @Override
    public void run() {
        Utils.printOut(listenPort, targetPort, "Connecting to client...");

        Random r = new Random();
        int id = r.nextInt((999999 - 100000) + 1) + 100000;
        clientId = Integer.toString(id);

        Utils.printOut(listenPort, targetPort, "Assigned client ID: " + clientId);
        Router.targetMap.put(clientId, socket);

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            output.println(Checksum.Add("ASSIGN|" + clientId));

            while (true) {
                String message = input.readLine();

                Utils.printOut(listenPort, targetPort, "Got message: " + message);
                if (message == null)
                    return;
                new MessageProcessor(listenPort, socket).processMessage(message);
            }
        } catch (IOException e) {
            Utils.printErr(listenPort, targetPort, "Error reading from socket: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Utils.printErr(listenPort, targetPort, "Error closing socket: " + e.getMessage());
            }

            Utils.printOut(listenPort, targetPort, "Disconnecting from client ID " + clientId);
            Router.targetMap.remove(clientId);
        }
    }
}
