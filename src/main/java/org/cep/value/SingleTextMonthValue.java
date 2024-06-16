package org.cep.value;

import org.cep.shared.Lazy;
import org.cep.shared.Month;

import java.util.List;

import static org.cep.shared.Month.valueOf;

class SingleTextMonthValue extends MonthValue {
    private final String rawValue;
    private final Lazy<Integer> value;

    public SingleTextMonthValue(String rawValue) {
        this.rawValue = rawValue;
        this.value = Lazy.of(() -> parseValue(rawValue));
    }

    private int parseValue(String rawValue) {
        return valueOf(rawValue.toUpperCase()).getNumber();
    }

    @Override
    public String getRawValue() {
        return rawValue;
    }

    @Override
    public ValueType getType() {
        return ValueType.SINGLE_TEXT_MONTH;
    }

    @Override
    public boolean isValid() {
        return Month.containsName(rawValue);
    }

    @Override
    public List<Integer> describe() {
        return List.of(value.get());
    }
}
