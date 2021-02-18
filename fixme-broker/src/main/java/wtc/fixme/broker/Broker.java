package wtc.fixme.broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class Broker {

    private static Boolean exitCase;
    private static final String ANSI_PURPLE = "\u001B[35m";
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            BufferedReader echoes = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String echoString;
            String response;


            do {
                exitCase = false;

                System.out.println(ANSI_PURPLE + "Buy, Sell, List Markets or Display your goods ");
                echoString = scanner.nextLine().toLowerCase();

                if (echoString.equals("buy")) {
                    System.out.println(ANSI_PURPLE + "Choose Market ID:");
                    String marketID = scanner.nextLine().toLowerCase();
                    System.out.println(ANSI_PURPLE + "Choose Item ID to purchase:");
                    String itemID = scanner.nextLine().toLowerCase();
                    System.out.println(ANSI_PURPLE + "Choose amount you'd like to purchase:");
                    String purchaseAmount = scanner.nextLine().toLowerCase();
                    System.out.println(ANSI_PURPLE + "Choose price you'd like to purchase for:");
                    String purchasePrice = scanner.nextLine().toLowerCase();
                    System.out.println(ANSI_PURPLE + "fix format: " + marketID + " " + itemID + " " + purchaseAmount + " " + purchasePrice);
                } else if  (echoString.equals("sell")) {
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
                    System.out.println("TODO: List markets");
                } else if (echoString.equals("goods")) {
                    System.out.println("TODO: List goods");
                }
            } while(!"exit".equals(echoString));

        } catch (IOException e) {
            System.out.println(ANSI_PURPLE + "Broker Client Error: " + e.getMessage());

        }
    }
}
