package com.example.androidatmapplication;

import java.util.Random;

public class Bank {
    private Customer customer = Customer.getInstance();
    static private ATM atm = ATM.getInstance();

    private static  Bank bank = Bank.getInstance();

    private Bank(){

    }

    static Bank init(){
        bank = new Bank();
        return bank;
    }

    static Bank getInstance(){
//        atm = ATM.getInstance();
        return bank;
    }

    boolean checkPin(){
        return customer.getPin().equalsIgnoreCase(atm.getPin());
    }

    boolean checkBalance(double withdrawAmount){
        return customer.getAccountBalance() >= withdrawAmount;
    }

    int getApproval(){
        Random random = new Random();
        return random.nextInt(2); // 0 = declined, 1 = approved
    }
}
