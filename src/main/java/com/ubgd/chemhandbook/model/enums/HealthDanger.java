package com.ubgd.chemhandbook.model.enums;

public enum HealthDanger {
    POISONOUS("Отруйна"),
    HARMFUL("Шкідлива для здоров'я"),
    CAUSTIC("Їдка"),
    ANNOYING("Подразнююча"),
    PENETRATING("Проникна");

    private String displayName;

    HealthDanger(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
