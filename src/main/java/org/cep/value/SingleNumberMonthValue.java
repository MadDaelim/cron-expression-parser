package org.cep.value;

import org.cep.shared.Lazy;

import java.util.List;

class SingleNumberMonthValue extends MonthValue {
    private final String rawValue;
    private final Lazy<Integer> value;

    public SingleNumberMonthValue(String rawValue) {
        this.rawValue = rawValue;
        this.value = Lazy.of(() -> parseValue(rawValue));
    }

    private int parseValue(String rawValue) {
        return Integer.parseInt(rawValue);
    }

    @Override
    public String getRawValue() {
        return rawValue;
    }

    @Override
    public ValueType getType() {
        return ValueType.SINGLE_NUMBER_MONTH;
    }

    @Override
    public boolean isValid() {
        return isNumber() && isInRange();
    }

    private boolean isNumber() {
        return rawValue.matches("\\d+");
    }

    private boolean isInRange() {
        return value.get() >= 1 && value.get() <= 12;
    }

    @Override
    public List<Integer> describe() {
        return List.of(value.get());
    }
}
