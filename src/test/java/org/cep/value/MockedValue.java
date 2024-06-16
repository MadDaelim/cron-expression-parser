package org.cep.value;

import java.util.ArrayList;
import java.util.List;

public class MockedValue implements Value {
    private String rawValue;
    private boolean valid;
    private ValueType type;
    private List<Integer> describe;
    private String error;

    public static MockedValue mockValue() {
        return new MockedValue();
    }

    public MockedValue rawValue(String rawValue) {
        this.rawValue = rawValue;
        return this;
    }

    public MockedValue valid(boolean valid) {
        this.valid = valid;
        return this;
    }

    public MockedValue type(ValueType type) {
        this.type = type;
        return this;
    }

    public MockedValue describe(Integer... describe) {
        this.describe = List.of(describe);
        return this;
    }

    public MockedValue error(String error) {
        this.error = error;
        return this;
    }

    @Override
    public String getRawValue() {
        return rawValue;
    }

    @Override
    public ValueType getType() {
        return type;
    }

    @Override
    public List<Integer> describe() {
        return describe;
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public String describeError() {
        return error;
    }

    @Override
    public String toString() {
        var result = new ArrayList<String>();

        if (rawValue != null) {
            result.add("rawValue='" + rawValue + '\'');
        }
        result.add("valid=" + valid);
        if (type != null) {
            result.add("type=" + type);
        }
        if (describe != null) {
            result.add("describe=" + describe);
        }
        if (error != null) {
            result.add("error='" + error);
        }

        return "MockedValue{" + String.join(", ", result) + "}";
    }
}
