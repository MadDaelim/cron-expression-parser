package org.cep.value;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HourValueTest {
    @Test
    void shouldCreateAnyHourValue() {
        var result = HourValue.create("*");

        assertThat(result).isInstanceOf(AnyHourValue.class);
    }

    @Test
    void shouldCreateSingleHourValue() {
        var result = HourValue.create("5");

        assertThat(result).isInstanceOf(SingleHourValue.class);
    }
}
