package org.cep.value;

import org.cep.shared.Lazy;

import java.util.List;

class SingleHourValue extends HourValue {
    private final String rawValue;
    private final Lazy<Integer> value;

    public SingleHourValue(String rawValue) {
        this.rawValue = rawValue;
        this.value = Lazy.of(() -> Integer.parseInt(rawValue));
    }

    @Override
    public String getRawValue() {
        return rawValue;
    }

    @Override
    public ValueType getType() {
        return ValueType.SINGLE_HOUR;
    }

    @Override
    public boolean isValid() {
        return isNumber() && isInRange();
    }

    private boolean isNumber() {
        return rawValue.matches("\\d+");
    }

    private boolean isInRange() {
        return value.get() <= MAX;
    }

    @Override
    public List<Integer> describe() {
        return List.of(value.get());
    }
}
