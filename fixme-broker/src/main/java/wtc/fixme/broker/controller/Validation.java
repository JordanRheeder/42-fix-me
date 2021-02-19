package wtc.fixme.broker.controller;


public class Validation {

    private static final String ANSI_RED = "\u001B[31m";

    public boolean validateInput(String[] input) {
        try {
            if (input.length != 4) {
                System.out.println(ANSI_RED + "Please enter the correct amount of parameters");
                return false;
            }
            if (!validateNumber(input[0].trim()) || !validateNumber(input[1].trim()) || !validateNumber(input[2].trim()) || !validateNumber(input[3].trim())) {
                System.out.println(ANSI_RED + "Make sure your input is numerical");
                return false;
            }
            if (Integer.parseInt(input[2].trim()) <= 0) {
                System.out.println("Order amount needs to be greater than 0");
                return false;
            }
            if (Integer.parseInt(input[3].trim()) <= 0) {
                System.out.print("Purchase price needs to be greater than 0");
                return false;
            }
            return true;
        } catch (NullPointerException e) {
            System.out.println("Incorrect amount of input parameters");
            return false;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect amount of input parameters");
            return false;
        }
    }

    private boolean validateNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
