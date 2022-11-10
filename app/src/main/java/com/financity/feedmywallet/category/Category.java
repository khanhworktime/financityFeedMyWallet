package com.financity.feedmywallet.category;

import android.text.Editable;

public class Category {

    public static final String[] CATEGORIES = {"Salary", "Gifted", "Business" , "Extra income" , "Loan", "Food & Drink", "Shopping", "Transport", "Home", "Bill & Fees", "Entertainment", "Car", "Travel", "Family", "Healthcare", "Education", "Groceries", "Gift"};
    public static final String[] CATEGORIES_INCOME = {"Salary", "Gifted", "Business" , "Extra income" , "Loan"};
    public static final String[] CATEGORIES_OUTCOME = {"Food & Drink", "Shopping", "Transport", "Home", "Bill & Fees", "Entertainment", "Car", "Travel", "Family", "Healthcare", "Education", "Groceries", "Gift"};
    public static final String[] CATEGORIES_TYPE = {"Income", "Outcome"};
    String value;
    String type;

    public Category(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    interface Type {
        String getType();
    }



}
