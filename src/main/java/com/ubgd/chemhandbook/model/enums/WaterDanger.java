package com.ubgd.chemhandbook.model.enums;

public enum WaterDanger {
    NOT_RECOMMENDED("Обережно з водою"),
    FORBIDDEN("Заборонено воду"),
    ABSENT("Відсутня");

    private String displayName;

    WaterDanger(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
