package com.ubgd.chemhandbook.model.enums;

public enum AggregationState {
    GASEOUS("Газоподібний"),
    LIQUID("Рідкий"),
    SOLID("Твердий"),
    TRANSITIONAL("Перехідний");

    private String displayName;

    AggregationState(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
