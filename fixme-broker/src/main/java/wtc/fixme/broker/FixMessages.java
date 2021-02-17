package wtc.fixme.broker;

import java.util.HashMap;

public class FixMessages {

    public HashMap<String, String> mapTag = new HashMap<>();
    public String fixHeader;
    public String messageBodyLength;
    public String messageTyoe;
    public String senderID;
    public String receiverID;
    public String Instrument;
    public String buyTag;
    public String sellTag;
    public String messageFromID;
    public String receivedMessage;
    public int quantity;
    public int price;

    public String getFixHeader() {
        mapTag.put("8", "FIX.4.2|");
        return "8=FIX.4.2|";
    }

    public String getMessageBodyLength() {
        mapTag.put("9", totalBodyLength() + "|");
        return "9=" + totalBodyLength() + "|";
    }


    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
    public String getSenderID() {
        return "49=" + senderID + "|";
    }


    public void setMessageFromID(String messageFromID) {
        this.messageFromID = messageFromID;
    }
    public String getMessageFromID() {
        return messageFromID;
    }


    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }
    public String getReceievedMessage() {
        return receivedMessage;
    }


    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }
    public String getReceiverID() {
        return "56=MARKET|";
    }
    
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getQuantity() {
        return "687=" + quantity + "|";
    }


    public void setPrice(int price) {
        this.price = price;
    }
    public String getPrice() {
        return "44=" + price + "|";
    }


    public void setInstrument(String instrument) {
        Instrument = instrument;
    }
    public String getInstrument() {
        return "460=" + Instrument + "|";
    }


    public String getBuyTag() {
        return "54=1|";
    }
    public String getSellTag() {
        return "54=2|";
    }
    public String getMessageType() {
        return "35=D|";
    }


    private int totalBodyLength() {
        String messageBody = getInstrument() + getPrice() + getQuantity() + getReceiverID() + getSenderID() + getBuyTag() + getMessageType();
        String newMessageString = messageBody.replace("|", "\u0001");
        return newMessageString.length();
    }
    
    public String fixBuyMessage() {
        StringBuilder message = new StringBuilder();

        message.append(getFixHeader());
        message.append(getMessageBodyLength());
        message.append(getSenderID());
        message.append(getMessageType());
        message.append(getInstrument());
        message.append(getQuantity());
        message.append(getReceiverID());
        message.append(getPrice());
        message.append(getBuyTag());

        return message.toString();
    }

    public String fixSellMessage() {
        StringBuilder message = new StringBuilder();

        message.append(getFixHeader());
        message.append(getMessageBodyLength());
        message.append(getSenderID());
        message.append(getMessageType());
        message.append(getInstrument());
        message.append(getQuantity());
        message.append(getReceiverID());
        message.append(getPrice());
        message.append(getBuyTag());

        return message.toString();
    }
}
