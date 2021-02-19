package wtc.fixme.broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

import wtc.fixme.core.Checksum;

public class Broker {
    public static String clientID;
    private static Boolean exitCase;
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private static void sendMessage(PrintWriter output, String message) {
        message = clientID + "|" + message;
        message = Checksum.Add(message);

        System.out.println("Sending " + message);
        output.println(message);
    }

    public static void main(String[] args) {
        String[] userInput = new String[4];
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
                exitCase = false;

                System.out.println(ANSI_PURPLE + "Buy, Sell, List Markets or Display your goods ");
                echoString = scanner.nextLine().toLowerCase();

                if (echoString.equals("buy")) {
                    //the first line in the message will be the broker ID
                    //this market id will either be to choose a market
                    //or it will be rmoved
                    System.out.println(ANSI_PURPLE + "Choose Market ID:");
                    userInput[0] = scanner.nextLine().toLowerCase();
                    System.out.println(ANSI_PURPLE + "Choose Item ID to purchase:");
                    userInput[1] = scanner.nextLine().toLowerCase();
                    System.out.println(ANSI_PURPLE + "Choose amount you'd like to purchase:");
                    userInput[2] = scanner.nextLine().toLowerCase();
                    System.out.println(ANSI_PURPLE + "Choose price you'd like to purchase for:");
                    userInput[3] = scanner.nextLine().toLowerCase();
                    sendBrokerReq(userInput, input, output);
                } else if (echoString.equals("sell")) {
                    System.out.println("Choose Market ID:");
                    String marketID = scanner.nextLine().toLowerCase();
                    System.out.println("Choose item ID to sell:");
                    String itemID = scanner.nextLine().toLowerCase();
                    System.out.println("Choose amount you'd like to sell:");
                    String saleAmount = scanner.nextLine().toLowerCase();
                    System.out.println("Choose price you'd like to sell for:");
                    String salePrice = scanner.nextLine().toLowerCase();
                    System.out.println(ANSI_PURPLE + "fix format: " + marketID + " " + itemID + " " + saleAmount + " " + salePrice);
                } else if (echoString.equals("list")) {
                    sendMessage(output, "LIST");

                    response = input.readLine();
                    System.out.println("Received: " + response);
                } else if (echoString.equals("goods")) {
                    System.out.println("TODO: List goods");
                }
            } while (!"exit".equals(echoString));

            scanner.close();


        } catch (IOException e) {
            System.out.println(ANSI_PURPLE + "Broker Client Error: " + e.getMessage());
        }
    }

    private static void sendBrokerReq(String[] req, BufferedReader input, PrintWriter output) throws IOException{
        //create a fix messages using array with users input
        //msg will then equal that fix message but for no it will equal a concatenated string

        String msg = String.join(" | ", req);

        if (msg != null) {
            System.out.println("Order: " + msg);

            try {
                output.println(msg);

                String fromRouter = input.readLine();

                if (fromRouter != null) {
                    System.out.println("Result: " + fromRouter);
                    if (fromRouter.contains("Rejected"))
                        System.out.println(ANSI_RED + "Rejected");
                    else if (fromRouter.contains("Accepted"))
                        System.out.println(ANSI_GREEN + "Accepted");
                }
                else System.out.println(ANSI_RED + "The result from the market was unidentifiable. Please try again later!");
            }catch (IOException e) {
                System.out.println(ANSI_RED + "\nUnable to communicate with the server. Please try again!\n");
            }
            //this else will be more useful when we can turn input into fix msgs
        } else {
            System.out.println(ANSI_RED + "FIX message could not be created! Please try again");
        }
    }
}
