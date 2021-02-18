package wtc.fixme.router;

public class Router {
    static int brokerPort = 5000;
    static int marketPort = 5001;

    public static void main(String args[]) {
        new Connector(brokerPort).start();
        new Connector(marketPort).start();
    }
}
