package com.financity.feedmywallet.wallet;

import com.google.firebase.ktx.Firebase;

import java.util.Random;

public class Wallet {
    String name;
    String currency;
    Float balance;
    String id;

    public Wallet() {
    }

    public Wallet(String id, String name, String currency, Float balance) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
