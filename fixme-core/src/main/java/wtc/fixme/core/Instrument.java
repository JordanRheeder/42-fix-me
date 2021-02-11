package Interfaces;

public interface Instrument {
    // getters
    String getProductID();
    String getSize();
    int getAmountAvailable();
    int getPrice();
    boolean checkAlias(String request);

    // setters
    void setAmountAvailable(int value);
    void setPrice(int value);
    void setProductID(String value);
    void setSize(String value);
}
