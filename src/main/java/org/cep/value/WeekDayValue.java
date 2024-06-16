package org.cep.value;

public abstract class WeekDayValue implements Value {
    protected static final int MIN = 0;
    protected static final int MAX = 6;

    public static WeekDayValue create(String token) {
        if (ANY_VALUE.equals(token)) {
            return new AnyWeekDayValue();
        } else if (token.matches("\\d+")) {
            return new SingleNumberWeekDayValue(token);
        }

        return new SingleTextWeekDayValue(token);
    }
}
