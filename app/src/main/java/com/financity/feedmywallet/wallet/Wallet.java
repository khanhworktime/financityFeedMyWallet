package com.financity.feedmywallet.wallet;

public class Wallet {
    String name;
    String currency;
    Integer balance;

    public Wallet() {
        name = "";
        currency = "đ";
        balance = 0;
    }

    public Wallet(String name, String currency, Integer balance) {
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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
