package wtc.fixme.router;

import java.io.IOException;
import java.net.ServerSocket;

public class Connector extends Thread {
    int listenPort;

    Connector(int _listenPort) {
        listenPort = _listenPort;
    }

    @Override
    public void run() {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(listenPort)) {
                System.out.println("[" + listenPort + "] Listening.");

                new Echoer(serverSocket.accept()).start();
                System.out.println("[" + listenPort + "] Connected.");

            } catch (IOException e) {
                System.err.println("[" + listenPort + "] Could not connect: " + e.getMessage());
            }
        }
    }
}
