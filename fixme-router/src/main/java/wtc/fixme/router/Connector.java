package wtc.fixme.router;

import java.io.IOException;
import java.net.ServerSocket;

public class Connector extends Thread {
    int listenPort;

    Connector(int listenPort) {
        this.listenPort = listenPort;
    }

    @Override
    public void run() {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(listenPort)) {
                Utils.printOut(listenPort, "Listening.");

                new Connection(listenPort, serverSocket.accept()).start();
                Utils.printOut(listenPort, "Connected.");

            } catch (IOException e) {
                Utils.printErr(listenPort, "Could not connect: " + e.getMessage());
            }
        }
    }
}
