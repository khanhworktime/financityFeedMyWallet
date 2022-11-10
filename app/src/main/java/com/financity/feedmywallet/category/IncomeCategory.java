package com.financity.feedmywallet.category;

public class IncomeCategory extends Category implements Category.Type{
    public IncomeCategory(String value) {
        super(value, "income");
    }

    @Override
    public String getType() {
        return "Income";
    }
}
