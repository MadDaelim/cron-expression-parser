package org.cep.value;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinuteValueTest {
    @Test
    void shouldCreateAnyMinuteValue() {
        var result = MinuteValue.create("*");

        assertThat(result).isInstanceOf(AnyMinuteValue.class);
    }

    @Test
    void shouldCreateSingleMinuteValue() {
        var result = MinuteValue.create("5");

        assertThat(result).isInstanceOf(SingleMinuteValue.class);
    }
}
