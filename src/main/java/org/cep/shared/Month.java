package org.cep.shared;

import java.util.Arrays;

public enum Month {
    JAN(1),
    FEB(2),
    MAR(3),
    APR(4),
    MAY(5),
    JUN(6),
    JUL(7),
    AUG(8),
    SEP(9),
    OCT(10),
    NOV(11),
    DEC(12);

    public static boolean containsName(String name) {
        return Arrays.stream(values()).anyMatch(month -> month.hasName(name));
    }

    private final int number;

    Month(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean hasName(String name) {
        return name().equalsIgnoreCase(name);
    }
}
