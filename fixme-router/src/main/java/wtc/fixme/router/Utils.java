package wtc.fixme.router;

public class Utils {
    public static String prefix(int listenPort) {
        return "[" + listenPort + "] ";
    }

    public static String prefix(int listenPort, int targetPort) {
        return "[" + listenPort + "=>" + targetPort + "] ";
    }
}
