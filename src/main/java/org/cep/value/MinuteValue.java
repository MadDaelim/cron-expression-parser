package org.cep.value;

public abstract class MinuteValue implements Value {
    protected static final int MIN = 0;
    protected static final int MAX = 59;

    public static MinuteValue create(String token) {
        if (ANY_VALUE.equals(token)) {
            return new AnyMinuteValue();
        }

        return new SingleMinuteValue(token);
    }
}
