package com.example.androidatmapplication.models;

import java.util.Random;

public class Bank {
    private Customer customer = Customer.getInstance();
    static private ATM atm = ATM.getInstance();

    private static  Bank bank = Bank.getInstance();

    private Bank(){

    }

    public static Bank init(){
        bank = new Bank();
        return bank;
    }

    public static Bank getInstance(){
//        atm = ATM.getInstance();
        return bank;
    }

    public boolean checkPin(){
        return customer.getPin().equalsIgnoreCase(atm.getPin());
    }

    public boolean checkBalance(double withdrawAmount){
        return customer.getAccountBalance() >= withdrawAmount;
    }

    public int getApproval(){
        Random random = new Random();
        return random.nextInt(2); // 0 = declined, 1 = approved
    }
}
