package com.financity.feedmywallet.budget;

import com.financity.feedmywallet.walletCard.Wallet;

public class Budget {
    int amount;
    String name;
    String note;
    boolean isStarted;
    Wallet wallet;
    String startDate;
    String endDate;

    public Budget() {
    }

    public Budget(int amount, String name, String note, boolean isStarted, Wallet wallet, String startDate, String endDate) {
        this.amount = amount;
        this.name = name;
        this.note = note;
        this.isStarted = isStarted;
        this.wallet = wallet;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }



    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDuration(){
        return this.startDate + " - " + this.endDate;
    }
}
