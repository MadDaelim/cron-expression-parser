package org.cep.value;

import java.util.List;

import static java.util.stream.Collectors.joining;

class ListValue implements Value {
    public static final String LIST_SEPARATOR = ",";

    private final List<Value> values;

    public ListValue(List<Value> values) {
        this.values = values;
    }

    @Override
    public String getRawValue() {
        return values.stream().map(Value::getRawValue).collect(joining(LIST_SEPARATOR));
    }

    @Override
    public ValueType getType() {
        return ValueType.LIST;
    }

    @Override
    public boolean isValid() {
        for (Value value : values) {
            if (!value.isValid()) return false;
        }
        return true;
    }

    @Override
    public List<Integer> describe() {
        return values.stream()
                .map(Value::describe)
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    @Override
    public String describeError() {
        return values.stream()
                .map(value -> {
                    if (value.isValid()) {
                        return value.getRawValue();
                    } else {
                        return value.describeError();
                    }
                }).collect(joining("\n")).indent(15).trim();
    }
}
