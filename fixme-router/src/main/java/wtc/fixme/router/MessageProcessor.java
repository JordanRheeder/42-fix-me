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
        PrintWriter srcOutput = new PrintWriter(socket.getOutputStream(), true);

        if (!Checksum.Validate(message)) {
            Utils.printOut(listenPort, targetPort, "Invalid Checksum, aborting");
            srcOutput.println("Invalid Checksum");
            return;
        }

        String[] msgArr = message.split("\\|");

        // srcID|LIST|checksum
        if (msgArr.length == 3 && msgArr[1].equals("LIST")) {
            Utils.printOut(listenPort, targetPort, "Market List requested, sending");
            srcOutput.println("Listing markets");
            return;
        }

        // srcID|targetID|message|checksum
        if (msgArr.length < 4) {
            Utils.printOut(listenPort, targetPort, "Invalid Message, aborting");
            srcOutput.println("Invalid Message (separator count)");
            return;
        }

        String targetID = msgArr[1];
        if (!Router.targetMap.containsKey(targetID)) {
            Utils.printOut(listenPort, targetPort, "TargetID \"" + targetID + "\" not found in map, aborting");
            srcOutput.println("TargetID not found");
            return;
        }

        Socket destSocket = Router.targetMap.get(targetID);
        Utils.printOut(listenPort, targetPort, "Sending message \"" + message + "\" to " + destSocket.getPort());

        try {
            PrintWriter output = new PrintWriter(destSocket.getOutputStream(), true);
            output.println(message);
        } catch (IOException e) {
            Utils.printErr(listenPort, targetPort, "Error sending message: " + e.getMessage());
            srcOutput.println("Error sending message: " + e.getMessage());
        }
    }
}
