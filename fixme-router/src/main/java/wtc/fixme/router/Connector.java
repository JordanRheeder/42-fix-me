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
                System.out.println(Utils.prefix(listenPort) + "Listening.");

                new Echoer(serverSocket.accept()).start();
                System.out.println(Utils.prefix(listenPort) + "Connected.");

            } catch (IOException e) {
                System.err.println(Utils.prefix(listenPort) + "Could not connect: " + e.getMessage());
            }
        }
    }
}
