package com.ubgd.chemhandbook.model.enums;

public enum DensityAir {
    LIGHTER("Легша за повітря"),
    SAME("Однакова з повітрям"),
    HEAVIER("Важча за повітря");

    private String displayName;

    DensityAir(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
