package org.cep.value;

import java.util.List;

public interface Value extends Comparable<Value> {
    String ANY_VALUE = "*";

    String getRawValue();

    ValueType getType();

    List<Integer> describe();

    boolean isValid();

    default String describeError() {
        return getRawValue() + " <- invalid value";
    }

    default boolean isAny() {
        return getType().isAny();
    }

    default boolean isSingle() {
        return getType().isSingle();
    }

    default boolean hasType(ValueType type) {
        return getType().equals(type);
    }

    default int compareTo(Value other) {
        if (isSingle() && other.isSingle() && other.hasType(getType())) {
            return describe().getFirst().compareTo(other.describe().getFirst());
        }

        throw new NotComparableException(getType(), other.getType());
    }
}
