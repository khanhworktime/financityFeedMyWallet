package com.financity.feedmywallet.transaction;

import com.financity.feedmywallet.category.Category;
import com.financity.feedmywallet.walletCard.Wallet;

public class Transaction {

    public static final String[] TRANSACTION_TYPE = {"Income", "Outcome"};
    public static final String TRANSACTION_TYPE_INCOME = "Income";
    public static final String TRANSACTION_TYPE_OUTCOME = "Outcome";

    String name;
    String type;
    Category category;
    Float amount;
    Wallet wallet;
    String date;
    String note;

    public Transaction() {
    }

    public Transaction(String name, String types, Category category, Float amount, Wallet wallet, String date, String note) {
        this.name = name;
        this.type = types;
        this.category = category;
        this.amount = amount;
        this.wallet = wallet;
        this.date = date;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
