package wtc.fixme.router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

public class Router {
    static int brokerPort = 5000;
    static int marketPort = 5001;

    public static HashMap<String, Socket> targetMap;

    private static void readInput() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String input = reader.readLine();

                switch (input) {
                    case "exit": case "done": case "quit":
                    case "e": case "d": case "q":
                        System.exit(0);
                    default:
                        System.err.println("Invalid Router command: \"" + input + "\"");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read input: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        System.out.println("[ROUTER] BOOT");
        targetMap = new HashMap<String, Socket>();

        new Connector(brokerPort).start();
        new Connector(marketPort).start();

        readInput();
    }
}
