package com.ubgd.chemhandbook.model.enums;

public enum DensityWater {
    LIGHTER("Легша за воду"),
    SAME("Однакова з водою"),
    HEAVIER("Важча за воду");

    private String displayName;

    DensityWater(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
