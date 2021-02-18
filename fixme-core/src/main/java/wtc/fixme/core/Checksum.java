package wtc.fixme.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class Checksum {
    public static String Add(String message) {
        String checksum = Calc(message);
        return message + '|' + checksum;
    }

    public static Boolean Validate(String message) {
        // get checksum out of message
        String checksum = message.substring(message.lastIndexOf('|') + 1);
        message = message.substring(0, message.lastIndexOf('|'));
        // calculate checksum and return true if they are equal
        checksum = checksum.trim();
        return Calc(message).equals(checksum);
    }

    private static String Calc(String message) {
        try {
            MessageDigest msDigest;
            msDigest = MessageDigest.getInstance("SHA-1");
            msDigest.update(message.getBytes("UTF-8"));
            return DatatypeConverter.printHexBinary(msDigest.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
