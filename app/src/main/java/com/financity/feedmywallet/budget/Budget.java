package com.financity.feedmywallet.budget;

import com.financity.feedmywallet.WalletCard.Wallet;

import java.util.ArrayList;
import java.util.Date;

public class Budget {
    int total;
    String name;
    String note;
    boolean isStarted;
    Wallet wallet;
    Date startDate, endDate;

    public Budget(int total, String name, String note, boolean isStarted, Wallet wallet, Date startDate, Date endDate) {
        this.total = total;
        this.name = name;
        this.note = note;
        this.isStarted = isStarted;
        this.wallet = wallet;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
