package com.example.androidatmapplication.models;

import com.example.androidatmapplication.models.Customer;

import java.io.Serializable;

public class Transaction implements Serializable {
    private Customer customer;
    private String date;
    private String time;
    private String location;
    private String transactionType;
    private String account;
    private String amount;
    private String availableBalance;

    public Transaction(Customer customer,
                       String date,
                       String time,
                       String location,
                       String transactionType,
                       String account,
                       String amount,
                       String availableBalance) {
        this.customer = customer;
        this.date = date;
        this.time = time;
        this.location = location;
        this.transactionType = transactionType;
        this.account = account;
        this.amount = amount;
        this.availableBalance = availableBalance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getDate(){
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getAccount() {
        return account;
    }

    public String getAmount() {
        return amount;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }
}
