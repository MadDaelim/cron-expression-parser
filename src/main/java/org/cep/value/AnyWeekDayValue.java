package org.cep.value;

import java.util.List;
import java.util.stream.IntStream;

class AnyWeekDayValue extends WeekDayValue {
    @Override
    public String getRawValue() {
        return ANY_VALUE;
    }

    @Override
    public ValueType getType() {
        return ValueType.ANY_WEEK_DAY;
    }

    @Override
    public List<Integer> describe() {
        return IntStream.range(MIN, MAX + 1).boxed().toList();
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
