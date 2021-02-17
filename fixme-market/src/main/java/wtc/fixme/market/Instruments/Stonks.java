package wtc.fixme.market.Instruments;

import java.util.ArrayList;
import java.util.List;

public class Pillows {
    protected String productID;
    protected String product;
    protected int price;
    protected int amountAvailable;
    protected List<String> acceptedValues = new ArrayList<String>();

    @Override
    public String toString() {
        return "Product: " + productID + " Product: " + product + " Price: " + price + " amount available: " + amountAvailable;
    }
}
