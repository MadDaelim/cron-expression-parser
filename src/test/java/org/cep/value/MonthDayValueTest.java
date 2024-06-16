package org.cep.value;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MonthDayValueTest {
    @Test
    void shouldCreateAnyMonthDayValue() {
        var result = MonthDayValue.create("*");

        assertThat(result).isInstanceOf(AnyMonthDayValue.class);
    }

    @Test
    void shouldCreateSingleMonthDayValue() {
        var result = MonthDayValue.create("5");

        assertThat(result).isInstanceOf(SingleMonthDayValue.class);
    }
}
