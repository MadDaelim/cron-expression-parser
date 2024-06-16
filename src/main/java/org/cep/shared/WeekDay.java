package org.cep.shared;

import java.util.Arrays;

public enum WeekDay {
    SUN(0),
    MON(1),
    TUR(2),
    WED(3),
    THU(4),
    FRI(5),
    SAT(6);

    public static boolean containsName(String name) {
        return Arrays.stream(values()).anyMatch(month -> month.hasName(name));
    }

    private final int number;

    WeekDay(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean hasName(String name) {
        return name().equalsIgnoreCase(name);
    }
}
