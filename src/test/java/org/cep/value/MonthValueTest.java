package org.cep.value;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MonthValueTest {
    @Test
    void shouldCreateAnyMonthValue() {
        var result = MonthValue.create("*");

        assertThat(result).isInstanceOf(AnyMonthValue.class);
    }

    @Test
    void shouldCreateSingleNumberMonthValue() {
        var result = MonthValue.create("5");

        assertThat(result).isInstanceOf(SingleNumberMonthValue.class);
    }

    @Test
    void shouldCreateTextMonthValue() {
        var result = MonthValue.create("JAN");

        assertThat(result).isInstanceOf(SingleTextMonthValue.class);
    }
}
