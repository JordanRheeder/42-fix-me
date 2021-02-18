package wtc.fixme.router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Router {
    static int brokerPort = 5000;
    static int marketPort = 5001;

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
        new Connector(brokerPort).start();
        new Connector(marketPort).start();

        readInput();
    }
}
