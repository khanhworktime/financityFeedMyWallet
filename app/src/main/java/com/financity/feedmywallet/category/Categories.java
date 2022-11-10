package com.financity.feedmywallet.category;

import java.util.ArrayList;

public class Categories {
    ArrayList<Category> incomeCategories;
    ArrayList<Category> outcomeCategories;

    public Categories() {
        incomeCategories = new ArrayList<>();
        outcomeCategories = new ArrayList<>();

        initCategories();
    }

    public Categories(ArrayList<Category> incomeCategories, ArrayList<Category> outcomeCategories) {
        this.incomeCategories = incomeCategories;
        this.outcomeCategories = outcomeCategories;
    }

    private void initCategories(){
//        Add income
        incomeCategories.add(new Category("Salary", "Income"));
        incomeCategories.add(new Category("Gifted", "Income"));
        incomeCategories.add(new Category("Business", "Income"));
        incomeCategories.add(new Category("Extra income", "Income"));
        incomeCategories.add(new Category("Loan", "Income"));
//        Add outcome
        outcomeCategories.add(new Category("Food & Drink", "Outcome"));
        outcomeCategories.add(new Category("Shopping", "Outcome"));
        outcomeCategories.add(new Category("Transport", "Outcome"));
        outcomeCategories.add(new Category("Home", "Outcome"));
        outcomeCategories.add(new Category("Bills & Fees", "Outcome"));
        outcomeCategories.add(new Category("Entertainment", "Outcome"));
        outcomeCategories.add(new Category("Car", "Outcome"));
        outcomeCategories.add(new Category("Travel", "Outcome"));
        outcomeCategories.add(new Category("Family", "Outcome"));
        outcomeCategories.add(new Category("Healthcare", "Outcome"));
        outcomeCategories.add(new Category("Education", "Outcome"));
        outcomeCategories.add(new Category("Groceries", "Outcome"));
        outcomeCategories.add(new Category("Gift", "Outcome"));
    }

    public ArrayList<Category> getIncomeCategories() {
        return incomeCategories;
    }

    public void setIncomeCategories(ArrayList<Category> incomeCategories) {
        this.incomeCategories = incomeCategories;
    }

    public ArrayList<Category> getOutcomeCategories() {
        return outcomeCategories;
    }

    public void setOutcomeCategories(ArrayList<Category> outcomeCategories) {
        this.outcomeCategories = outcomeCategories;
    }

    public Category getOutcomeCategory(String name) {
        for (int i = 0; i < outcomeCategories.size(); i++) {
            if (outcomeCategories.get(i).getValue().equals(name)) {
                return outcomeCategories.get(i);
            }
        }
        return null;
    }

    public Category getIncomeCategory(String name) {
        for (int i = 0; i < incomeCategories.size(); i++) {
            if (incomeCategories.get(i).getValue().equals(name)) {
                return incomeCategories.get(i);
            }
        }
        return null;
    }
}
