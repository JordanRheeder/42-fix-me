package wtc.fixme.broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import wtc.fixme.core.Checksum;

public class Broker {
    public static String clientID;

    public static void main(String[] args) {
        System.out.println("[BROKER] BOOT");
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to port 5000 from port " + socket.getLocalPort());

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String assignMsg = input.readLine();
            if (!Checksum.Validate(assignMsg)) {
                System.err.println("Invalid checksum for ASSIGN message! Aborting");
                return;
            }

            // remove checksum and ASSIGN| at the same time
            assignMsg = assignMsg.substring(7, assignMsg.lastIndexOf('|'));
            clientID = assignMsg;
            System.out.println("Received clientID \"" + clientID + "\"");

            Scanner scanner = new Scanner(System.in);
            String echoString;
            String response;

            do {
                System.out.println("Enter string to be echoed: ");
                echoString = scanner.nextLine();

                if (!echoString.equals("exit")) {
                    echoString = Checksum.Add(echoString);
                    System.out.println("Sending " + echoString);
                    output.println(echoString);

                    response = input.readLine();
                    System.out.println(response);
                }
            } while (!echoString.equals("exit"));

            scanner.close();
        } catch (IOException e) {
            System.out.println("Broker Client Error: " + e.getMessage());
        }
    }
}
