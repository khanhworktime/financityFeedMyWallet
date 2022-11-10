package com.financity.feedmywallet.category;

import java.util.ArrayList;

public class Categories {
    ArrayList<IncomeCategory> incomeCategories;
    ArrayList<OutcomeCategory> outcomeCategories;

    public Categories() {
        incomeCategories = new ArrayList<>();
        outcomeCategories = new ArrayList<>();

        initCategories();
    }

    public Categories(ArrayList<IncomeCategory> incomeCategories, ArrayList<OutcomeCategory> outcomeCategories) {
        this.incomeCategories = incomeCategories;
        this.outcomeCategories = outcomeCategories;
    }

    private void initCategories(){
//        Add income
        incomeCategories.add(new IncomeCategory("Salary"));
        incomeCategories.add(new IncomeCategory("Gifted"));
        incomeCategories.add(new IncomeCategory("Business"));
        incomeCategories.add(new IncomeCategory("Extra income"));
        incomeCategories.add(new IncomeCategory("Loan"));
//        Add outcome
        outcomeCategories.add(new OutcomeCategory("Food & Drink"));
        outcomeCategories.add(new OutcomeCategory("Shopping"));
        outcomeCategories.add(new OutcomeCategory("Transport"));
        outcomeCategories.add(new OutcomeCategory("Home"));
        outcomeCategories.add(new OutcomeCategory("Bills & Fees"));
        outcomeCategories.add(new OutcomeCategory("Entertainment"));
        outcomeCategories.add(new OutcomeCategory("Car"));
        outcomeCategories.add(new OutcomeCategory("Travel"));
        outcomeCategories.add(new OutcomeCategory("Family"));
        outcomeCategories.add(new OutcomeCategory("Healthcare"));
        outcomeCategories.add(new OutcomeCategory("Education"));
        outcomeCategories.add(new OutcomeCategory("Groceries"));
        outcomeCategories.add(new OutcomeCategory("Gift"));
    }

    public ArrayList<IncomeCategory> getIncomeCategories() {
        return incomeCategories;
    }

    public void setIncomeCategories(ArrayList<IncomeCategory> incomeCategories) {
        this.incomeCategories = incomeCategories;
    }

    public ArrayList<OutcomeCategory> getOutcomeCategories() {
        return outcomeCategories;
    }

    public void setOutcomeCategories(ArrayList<OutcomeCategory> outcomeCategories) {
        this.outcomeCategories = outcomeCategories;
    }

    public OutcomeCategory getOutcomeCategory(String name) {
        for (int i = 0; i < outcomeCategories.size(); i++) {
            if (outcomeCategories.get(i).getValue().equals(name)) {
                return outcomeCategories.get(i);
            }
        }
        return null;
    }

    public IncomeCategory getIncomeCategory(String name) {
        for (int i = 0; i < incomeCategories.size(); i++) {
            if (incomeCategories.get(i).getValue().equals(name)) {
                return incomeCategories.get(i);
            }
        }
        return null;
    }
}
