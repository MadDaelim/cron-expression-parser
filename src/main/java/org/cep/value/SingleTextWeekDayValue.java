package org.cep.value;

import org.cep.shared.Lazy;
import org.cep.shared.WeekDay;

import java.util.List;

class SingleTextWeekDayValue extends WeekDayValue {
    private final String rawValue;
    private final Lazy<Integer> value;

    public SingleTextWeekDayValue(String rawValue) {
        this.rawValue = rawValue;
        this.value = Lazy.of(() -> parseValue(rawValue));
    }

    private int parseValue(String rawValue) {
        return WeekDay.valueOf(rawValue.toUpperCase()).getNumber();
    }

    @Override
    public String getRawValue() {
        return rawValue;
    }

    @Override
    public ValueType getType() {
        return ValueType.SINGLE_TEXT_WEEK_DAY;
    }

    @Override
    public boolean isValid() {
        return WeekDay.containsName(rawValue);
    }

    @Override
    public List<Integer> describe() {
        return List.of(value.get());
    }
}
