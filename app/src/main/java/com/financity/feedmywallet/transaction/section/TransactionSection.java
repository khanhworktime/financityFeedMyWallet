package com.financity.feedmywallet.transaction.section;

import com.financity.feedmywallet.transaction.Transaction;

import java.util.ArrayList;
import java.util.Date;

public class TransactionSection {
    String name;
    ArrayList<Transaction> transactions;
    String transDate;

    public TransactionSection(String name, ArrayList<Transaction> transactions, String transDate) {
        this.name = name;
        this.transactions = transactions;
        this.transDate = transDate;
    }

    public TransactionSection() {
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "TransactionSection{" +
                "name='" + name + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
