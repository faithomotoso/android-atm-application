package com.example.androidatmapplication.models;


public class ATM {
    static private String pin;
    private static ATM atm = null;
    private Customer customer = Customer.getInstance();
    static private Bank bank;
    static public int passwordTries;

    private ATM(){

    };

    public static ATM init(){
        atm = new ATM();
        passwordTries = 3;
        return atm;
    }

    public static ATM getInstance(){
        bank = Bank.getInstance();
        return atm;
    }

    public String getPin(){
        // pin entered by the user
        return pin;
    }

    public void setPin(String pin){
        this.pin = pin;
    }

    public static void clearInstance(){
        atm = null;
        pin = null;
    }

    public String performTransaction(double withdrawAmount){
        if (bank.checkBalance(withdrawAmount)){
            int approval = bank.getApproval();
            if (approval == 1){
                customer.changeAccountBalance(withdrawAmount);
                return "Transaction Successful";
            } else if (approval == 0){
                return "Transaction Declined by Institution";
            }
        } else {
            return "Insufficient Balance";
        }

        return "";
    }
}
