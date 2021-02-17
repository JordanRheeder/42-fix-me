package wtc.fixme.market.Instruments;

public interface Instrument {
    // getters
    String getProductID();
    String getProduct();
    int getAmountAvailable();
    int getPrice();
    boolean checkAlias(String request);

    // setters
    void setAmountAvailable(int value);
    void setPrice(int value);
    void setProductID(String value);
    void setProduct(String value);
}
