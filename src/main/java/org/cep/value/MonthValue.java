package org.cep.value;

public abstract class MonthValue implements Value {
    protected static final int MIN = 1;
    protected static final int MAX = 12;

    public static MonthValue create(String token) {
        if (ANY_VALUE.equals(token)) {
            return new AnyMonthValue();
        } else if (token.matches("\\d+")) {
            return new SingleNumberMonthValue(token);
        }

        return new SingleTextMonthValue(token);
    }
}
