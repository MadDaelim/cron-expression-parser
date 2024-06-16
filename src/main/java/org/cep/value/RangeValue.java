package org.cep.value;

import java.util.List;
import java.util.stream.IntStream;

class RangeValue implements Value {
    public static final String RANGE_SEPARATOR = "-";

    private final Value from;
    private final Value to;

    public RangeValue(Value from, Value to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String getRawValue() {
        return from.getRawValue() + RANGE_SEPARATOR + to.getRawValue();
    }

    @Override
    public ValueType getType() {
        return ValueType.RANGE;
    }

    @Override
    public boolean isValid() {
        return isValid(from) && isValid(to) && isRangeValid();
    }

    private boolean isValid(Value value) {
        return value.isValid() && value.isSingle() && !value.isAny();
    }

    private boolean isRangeValid() {
        return from.hasType(to.getType()) && from.compareTo(to) <= 0;
    }

    @Override
    public List<Integer> describe() {
        var from = this.from.describe().getFirst();
        var to = this.to.describe().getFirst() + 1;
        return IntStream.range(from, to).boxed().toList();
    }

    @Override
    public String describeError() {
        if (from.isAny() || to.isAny()) {
            return getRawValue() + " <- is not allowed to use * in range values";
        }
        if (!from.hasType(to.getType())) {
            return getRawValue() + " <- from range value should have same type as to value";
        }
        if (!from.isValid() || !to.isValid()) {
            if (from.isValid()) {
                return getRawValue() + " <- to range value is invalid";
            } else if (to.isValid()) {
                return getRawValue() + " <- from range value is invalid";
            } else {
                return getRawValue() + " <- both range values are invalid values";
            }
        }
        if (!isRangeValid()) {
            return getRawValue() + " <- from range value should be smaller that to range value";
        }
        return "";
    }
}
