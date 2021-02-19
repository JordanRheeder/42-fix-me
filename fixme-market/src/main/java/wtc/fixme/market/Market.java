package wtc.fixme.market;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import wtc.fixme.core.Checksum;
import wtc.fixme.market.Instruments.Stonks;
import wtc.fixme.market.Instruments.StockMarket;

public class Market {
    public static String clientID;
    public List<Stonks> stonks = new ArrayList<Stonks>();

    public static void main(String[] args) {
        System.out.println("[MARKET] BOOT");
        try (Socket socket = new Socket("localhost", 5001)) {
            System.out.println("Connected to port 5001 from port " + socket.getLocalPort());

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String assignMsg = input.readLine();
            if (!Checksum.Validate(assignMsg)) {
                System.err.println("Invalid checksum for ASSSIGN message! Aborting");
                return;
            }

            // remove checksum and ASSIGN| at the same time
            assignMsg = assignMsg.substring(7, assignMsg.lastIndexOf('|'));
            clientID = assignMsg;
            System.out.println("Received clientID \"" + clientID + "\"");

            System.out.println("Listening for messages...");
            while (true) {
                String message = input.readLine();

                System.out.println("Got message: " + message);
                processMessage(message, output);
            }
        } catch (IOException e) {
            System.out.println("Market Client Error: " + e.getMessage());

        }
    }

    private static void sendResponse(String returnID, String message, PrintWriter output) {
        if (returnID == null)
            return;
        if (message == null)
            return;

        message = clientID + "|" + returnID + "|" + message;
        message = Checksum.Add(message);

        System.out.println("Sending Message \"" + message + "\"");
        output.println(message);
    }

    private static void processMessage(String message, PrintWriter output) {
        if (!message.contains("|")) {
            System.out.println("No separator, could not get returnID");
            return;
        }

        // srcID|targetID|message|checksum
        String[] msgArr = message.split("\\|");

        if (!Checksum.Validate(message)) {
            System.out.println("Invalid Checksum");
            sendResponse(msgArr[0], "Invalid Checksum", output);
            return;
        }

        if (msgArr.length < 4) {
            System.out.println("Invalid Message");
            sendResponse(msgArr[0], "Invalid Message (separator count)", output);
        }

        sendResponse(msgArr[0], "HERE'S YOUR RESPONSE", output);

        if (message.contains("buy") || message.contains("sell")) {
            if (msgArr[2].equals("Game Stop") || msgArr[2].equals("Tesla") || msgArr[2].equals("Amazon") ||
            msgArr[2].equals("Apple") || msgArr[2].equals("Microsoft") || msgArr[2].equals("Netflix")) {
                if (Integer.parseInt(msgArr[3]) <= 500) {
                    if (Integer.parseInt(msgArr[4]) >= 1000) {
                        sendResponse(msgArr[0], "Approved", output);
                    } else sendResponse(msgArr[0], "Rejected", output);
                } else sendResponse(msgArr[0], "Rejected", output);
            } else sendResponse(msgArr[0], "Rejected", output);
        }
    }
}
