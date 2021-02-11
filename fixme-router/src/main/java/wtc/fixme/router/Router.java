package router;

import java.net.*;
import java.io.*;

public class Router {
    static int brokerPort = 5000;
    static int marketPort = 5001;

    public static void main(String args[]) {
        while(true) {
            try (ServerSocket serverSocket = new ServerSocket(brokerPort)) {
                new Echoer(serverSocket.accept()).start();
                System.out.println("Broker connected");

            } catch (IOException e) {
                System.out.println("Router error:1 " + e.getMessage());
            }
            try (ServerSocket serverSocket = new ServerSocket(marketPort)) {
                new Echoer(serverSocket.accept()).start();
                System.out.println("Market connected");
            } catch (IOException e) {
                System.out.println("Router error:2 " + e.getMessage());
            }
        }
    }
}
