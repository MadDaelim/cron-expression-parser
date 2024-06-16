package org.cep.value;

@FunctionalInterface
public interface ValueFactory {
    Value create(String rawValue);
}
