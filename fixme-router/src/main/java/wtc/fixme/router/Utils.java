package wtc.fixme.router;

public class Utils {
    public static void printOut(int listenPort, String message) {
        System.out.println("[" + listenPort + "] " + message);
    }

    public static void printOut(int listenPort, int targetPort, String message) {
        System.out.println("[" + listenPort + "=>" + targetPort + "] " + message);
    }

    public static void printErr(int listenPort, String message) {
        System.err.println("[" + listenPort + "] " + message);
    }

    public static void printErr(int listenPort, int targetPort, String message) {
        System.err.println("[" + listenPort + "=>" + targetPort + "] " + message);
    }
}
