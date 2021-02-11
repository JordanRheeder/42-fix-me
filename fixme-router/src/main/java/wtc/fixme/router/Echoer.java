package router;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

public class Echoer extends Thread {
    private Socket socket;
    private String clientId;

    Echoer(Socket socket) {
        System.out.println("port " + socket.getPort());
        this.socket = socket;
        Random r = new Random();
        int id = r.nextInt((999999 - 100000) + 1) + 100000;
        this.clientId = Integer.toString(id);
        System.out.println("\u001B[36mID: "+ clientId);
    }

    @Override
    public void run() {
        portThread(socket);
    }

    private static void portThread(Socket socket) {
        String ANSI_CYAN = "\u001B[36m";
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            while(true) {
                String echoString = input.readLine();
                if(echoString.equals("exit")) {
                    break;
                }
                System.out.println(ANSI_CYAN + echoString);
            }
        } catch(IOException e) {
            System.out.println("Oops: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch(IOException e) {
                System.out.println("123"); //fix
            }
        }
    }
}