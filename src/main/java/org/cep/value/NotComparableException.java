package org.cep.value;

public class NotComparableException extends RuntimeException {
    public NotComparableException(ValueType vt1, ValueType vt2) {
        super(vt1.toString() + " is not comparable to " + vt2.toString());
    }
}
