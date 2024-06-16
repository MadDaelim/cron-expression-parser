package org.cep.value;

public abstract class HourValue implements Value {
    protected static final int MIN = 0;
    protected static final int MAX = 23;

    public static HourValue create(String token) {
        if (ANY_VALUE.equals(token)) {
            return new AnyHourValue();
        }

        return new SingleHourValue(token);
    }
}
