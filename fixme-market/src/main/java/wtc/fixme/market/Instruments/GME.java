package wtc.fixme.market.Instruments;

import wtc.fixme.market.Model.Instrument;

public class GME extends Stonks implements Instrument {

    public GME() {
        this.productID = "GME";
        this.product = "Game Stop";
        this.price = 49;
        this.amountAvailable = 1000;
    }


    @Override
    public String getProductID() {
        return this.productID;
    }

    @Override
    public String getProduct() {
        return this.product;
    }

    @Override
    public int getAmountAvailable() {
        return this.amountAvailable;
    }

    @Override
    public int getPrice() {
        return this.amountAvailable;
    }

    @Override
    public void setAmountAvailable(int value) {
        this.amountAvailable = value;
    }

    @Override
    public void setPrice(int value) {
        this.price = value;
    }

    @Override
    public void setProductID(String value) {
        this.productID = value;
    }

    @Override
    public void setProduct(String value) {
        this.product = value;
    }

}