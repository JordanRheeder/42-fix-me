package wtc.fixme.router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Router {
    static int brokerPort = 5000;
    static int marketPort = 5001;

    public static HashMap<String, Socket> targetMap;

    public static void main(String args[]) {
        System.out.println("[ROUTER] BOOT");
        targetMap = new HashMap<String, Socket>();

        new Connector(brokerPort).start();
        new Connector(marketPort).start();

        readInput();
    }

    private static void readInput() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String input = reader.readLine();

                switch (input) {
                    case "exit": case "done": case "quit":
                    case "e": case "d": case "q":
                        System.exit(0);
                    case "list": case "clients": case "l": case "ls":
                        System.out.println("Connected clients: " + targetMap.size());
                        for (Map.Entry<String, Socket> entry : targetMap.entrySet()) {
                            System.out.print("ClientID:" + entry.getKey());
                            System.out.print(" Port:" + entry.getValue().getLocalPort());
                            System.out.println(" Remote:" + entry.getValue().getPort());
                        }
                        break;
                    default:
                        System.err.println("Invalid Router command: \"" + input + "\"");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read input: " + e.getMessage());
        }
    }
}
