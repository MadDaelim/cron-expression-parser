package org.cep.value;

public abstract class MonthDayValue implements Value {
    protected static final int MIN = 1;
    protected static final int MAX = 31;

    public static MonthDayValue create(String token) {
        if (ANY_VALUE.equals(token)) {
            return new AnyMonthDayValue();
        }

        return new SingleMonthDayValue(token);
    }
}
