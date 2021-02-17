package wtc.fixme.market.Instruments;

public class Stonks {
    protected String productID;
    protected String product;
    protected int price;
    protected int amountAvailable;

    public String printInstrument() {
        return "Product: " + productID + " Product: " + product + " Price: " + price + " amount available: " + amountAvailable;
    }
}
