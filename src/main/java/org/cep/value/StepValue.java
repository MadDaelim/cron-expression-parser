package org.cep.value;

import java.util.ArrayList;
import java.util.List;

class StepValue implements Value {
    public static final String STEP_SEPARATOR = "/";

    private final Value divider;
    private final Value divisor;

    public StepValue(Value divider, Value divisor) {
        this.divider = divider;
        this.divisor = divisor;
    }

    @Override
    public String getRawValue() {
        return divider.getRawValue() + STEP_SEPARATOR + divisor.getRawValue();
    }

    @Override
    public ValueType getType() {
        return ValueType.STEP;
    }

    @Override
    public boolean isValid() {
        return isValidDivider() && isValidDivisor();
    }

    private boolean isValidDivider() {
        return divider.isValid() && (divider.isAny() || isDividerRange());
    }

    private boolean isDividerRange() {
        return divider.getType() == ValueType.RANGE;
    }

    private boolean isValidDivisor() {
        return divisor.isValid() && !divisor.isAny();
    }

    @Override
    public List<Integer> describe() {
        var divisor = this.divisor.describe().getFirst();
        return divider.describe().stream().filter(value -> value % divisor == 0).toList();
    }

    @Override
    public String describeError() {
        var errors = new ArrayList<String>();
        if (divider.isSingle()) {
            errors.add("is not allowed to use single values as divider value");
        } else if (!divider.isValid()) {
            errors.add("divider value is invalid");
        }
        if (divisor.isAny()) {
            errors.add("is not allowed to use * in as divisor value");
        } else if (!divisor.isValid()) {
            errors.add("divisor value is invalid");
        }

        return getRawValue() + " <- " + String.join(", ", errors);
    }
}
