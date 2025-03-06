package com.ubgd.chemhandbook.model.enums;

public enum FlammabilityClass {
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4");

    private String description;

    FlammabilityClass(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
