package wtc.fixme.market.Model;

public interface Instrument {
    String getProductID();
    String getProduct();
    int getAmountAvailable();
    int getPrice();


    void setAmountAvailable(int value);
    void setPrice(int value);
    void setProductID(String value);
    void setProduct(String value);

}
