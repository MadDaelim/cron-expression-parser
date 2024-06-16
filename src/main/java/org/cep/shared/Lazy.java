package org.cep.shared;

import java.util.function.Supplier;

public class Lazy<T> {
    private boolean initialized = false;
    private T value;
    private final Supplier<T> supplier;

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (!initialized) {
            value = supplier.get();
            initialized = true;
        }

        return value;
    }
}
