package wtc.fixme.router;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import wtc.fixme.core.Checksum;

public class MessageProcessor {
    private int sourcePort;
    private int targetPort;

    public MessageProcessor(int sourcePort, int targetPort) {
        this.sourcePort = sourcePort;
        this.targetPort = targetPort;
    }

    public void processMessage(String message) {
        long pipeCount = message.chars().filter(ch -> ch == '|').count();
        if (pipeCount < 2) {
            Utils.printOut(sourcePort, targetPort, "Invalid Message, aborting");
            return;
        }

        if (!Checksum.Validate(message)) {
            Utils.printOut(sourcePort, targetPort, "Invalid Checksum, aborting");
            return;
        }

        String targetID = message.substring(0, message.indexOf('|'));
        if (!Router.targetMap.containsKey(targetID)) {
            Utils.printOut(sourcePort, targetPort, "TargetID \"" + targetID + "\" not found in map, aborting");
            return;
        }

        Socket destSocket = Router.targetMap.get(targetID);
        Utils.printOut(sourcePort, targetPort, "Sending message \"" + message + "\" to " + destSocket.getPort());

        try {
            PrintWriter output = new PrintWriter(destSocket.getOutputStream(), true);
            output.println(message);
        } catch (IOException e) {
            Utils.printErr(sourcePort, targetPort, "Error sending message: " + e.getMessage());
        }
    }
}
