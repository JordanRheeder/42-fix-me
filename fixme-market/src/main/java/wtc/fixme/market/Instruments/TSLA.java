package wtc.fixme.market.Instruments;

import wtc.fixme.market.Model.Instrument;

public class TSLA extends Stonks implements Instrument {

    public TSLA() {
        this.productID = "TSLA";
        this.product = "Tesla";
        this.price = 490;
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