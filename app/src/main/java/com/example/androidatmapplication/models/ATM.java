package com.example.androidatmapplication.models;


public class ATM {
    static private String pin;
    private static ATM atm = null;
    private Customer customer = Customer.getInstance();
    static private Bank bank;
    static public int passwordTries;
    public double withdrawAmount = 0;

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

    public boolean canWithdrawOtherAmount(){
        // only true for multiples of 500 and 1000
        if (withdrawAmount < 500){
            return false;
        } else if (withdrawAmount > 500 && withdrawAmount < 1000){
            return false;
        } else if (withdrawAmount == 500 || withdrawAmount == 1000){
            return true;
        } else if (withdrawAmount > 1000){
            double mod = withdrawAmount % 1000;
            if (mod == 500 || mod == 0){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
