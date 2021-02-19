package wtc.fixme.market.Instruments;

import java.util.Random;

public class Stonks {
    Random rand = new Random();
    protected String productID;
    protected String product;
    protected int price;
    protected int amountAvailable;

    public Stonks(String productID, String product) {
        this.productID = productID;
        this.product = product;
        this.price = 500;
        this.amountAvailable =1000;
    }
}
