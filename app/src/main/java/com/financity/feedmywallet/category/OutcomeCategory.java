package com.financity.feedmywallet.category;

public class OutcomeCategory extends Category implements Category.Type{
    public OutcomeCategory(String value) {
        super(value, "outcome");
    }

    @Override
    public String getType() {
        return "Outcome";
    }
}
