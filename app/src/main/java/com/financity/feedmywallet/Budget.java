package com.financity.feedmywallet;

import com.financity.feedmywallet.WalletCard.Wallet;

import java.util.ArrayList;
import java.util.Date;

public class Budget {
    int total;
    String name;
    ArrayList<String> groups;
    String note;
    boolean isStarted;
    Wallet wallet;
    Date startDate;
    String currency;

    public Budget(String currency,int total, String name, ArrayList<String> groups, String note, boolean isStarted, Wallet wallet, Date startDate) {
        this.total = total;
        this.name = name;
        this.groups = groups;
        this.note = note;
        this.isStarted = isStarted;
        this.wallet = wallet;
        this.startDate = startDate;
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
