package org.cep.value;

public enum ValueType {
    ANY_MINUTE(false, true),
    SINGLE_MINUTE(true, false),
    ANY_HOUR(false, true),
    SINGLE_HOUR(true, false),
    ANY_MONTH_DAY(false, true),
    SINGLE_MONTH_DAY(true, false),
    ANY_MONTH(false, true),
    SINGLE_TEXT_MONTH(true, false),
    SINGLE_NUMBER_MONTH(true, false),
    ANY_WEEK_DAY(false, true),
    SINGLE_TEXT_WEEK_DAY(true, false),
    SINGLE_NUMBER_WEEK_DAY(true, false),
    LIST(false, false),
    RANGE(false, false),
    STEP(false, false);

    private final boolean single;
    private final boolean any;

    ValueType(boolean single, boolean any) {
        this.single = single;
        this.any = any;
    }

    public boolean isSingle() {
        return single;
    }

    public boolean isAny() {
        return any;
    }
}
