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
                System.out.println("Listening on port " + listenPort);

                new Echoer(serverSocket.accept()).start();
                System.out.println("Connected on port " + listenPort);

            } catch (IOException e) {
                System.err.println("Could not connect: " + e.getMessage());
            }
        }
    }
}
