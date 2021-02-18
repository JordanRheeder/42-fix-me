package wtc.fixme.router;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import wtc.fixme.core.Checksum;

public class MessageProcessor {
    private int listenPort;
    private int targetPort;
    private Socket socket;

    public MessageProcessor(int listenPort, Socket socket) {
        this.listenPort = listenPort;
        this.targetPort = socket.getPort();
        this.socket = socket;
    }

    public void processMessage(String message) throws IOException {
        PrintWriter fromOutput = new PrintWriter(socket.getOutputStream(), true);

        long pipeCount = message.chars().filter(ch -> ch == '|').count();
        if (pipeCount < 2) {
            Utils.printOut(listenPort, targetPort, "Invalid Message, aborting");
            fromOutput.println("Invalid Message (separator count)");
            return;
        }

        if (!Checksum.Validate(message)) {
            Utils.printOut(listenPort, targetPort, "Invalid Checksum, aborting");
            fromOutput.println("Invalid Checksum");
            return;
        }

        String targetID = message.substring(0, message.indexOf('|'));
        if (!Router.targetMap.containsKey(targetID)) {
            Utils.printOut(listenPort, targetPort, "TargetID \"" + targetID + "\" not found in map, aborting");
            fromOutput.println("TargetID not found");
            return;
        }

        Socket destSocket = Router.targetMap.get(targetID);
        Utils.printOut(listenPort, targetPort, "Sending message \"" + message + "\" to " + destSocket.getPort());

        try {
            PrintWriter output = new PrintWriter(destSocket.getOutputStream(), true);
            output.println(message);
        } catch (IOException e) {
            Utils.printErr(listenPort, targetPort, "Error sending message: " + e.getMessage());
            fromOutput.println("Error sending message: " + e.getMessage());
        }
    }
}
