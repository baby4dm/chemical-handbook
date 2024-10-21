package com.ubgd.chemhandbook.model.enums;

public enum GeneralDanger {
    FLAMMABLE("Горюча"),
    EXPLOSIVE("Вибухонебезпечна"),
    RADIOACTIVE("Радіоактивна");

    public String displayName;

    public String getDisplayName() {
        return displayName;
    }

    GeneralDanger(String displayName) {
        this.displayName = displayName;
    }
}
