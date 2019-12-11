package com.example.androidatmapplication.models;

import java.io.Serializable;

public class Customer implements Serializable {
    // singleton class for each customer
    private static Customer customer = null;
    private String name;
    private double accountBalance;
    private String cardNumber;
    private String pin; // pin determined by card number, stays constant. Compare in the Bank class

    private Customer(String name, double acctBalance, String cardNumber, String pin){
        this.name = name;
        this.accountBalance = acctBalance;
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    public static Customer init(String name, double acctBalance, String cardNumber, String pin){
//        if (customer == null){
//            customer = new Customer(name, acctBalance, cardNumber, pin);
//        }
        customer = new Customer(name, acctBalance, cardNumber, pin);
        return customer;
    }

    public static Customer getInstance(){
        return customer;
    }

    public static void clearInstance(){
        customer = null;
    }


    public String getName(){
        return name;
    }

    public double getAccountBalance(){
        return accountBalance;
    }

    public void changeAccountBalance(double withdrawAmount){
        this.accountBalance -= withdrawAmount;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public String getPin(){
        return pin;
    }
}
