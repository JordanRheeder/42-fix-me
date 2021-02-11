package Interfaces;

public interface BrokerInterface {
    String getID();
    int getBalance();
    int getStockBalance();
    void addStock();
    void removeStock();

    void setID();
    void setBalance();


}
