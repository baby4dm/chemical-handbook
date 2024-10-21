package com.ubgd.chemhandbook.model.enums;

public enum Solubility {
    SOLUBLE("Водорозчинна"),
    LIMITED_SOLUBLE("Обмежено-розчинна"),
    NOT_SOLUBLE("Нерозчинна");

    private String displayName;

    Solubility(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
