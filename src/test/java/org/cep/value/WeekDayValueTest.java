package org.cep.value;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WeekDayValueTest {
    @Test
    void shouldCreateAnyWeekDayValue() {
        var result = WeekDayValue.create("*");

        assertThat(result).isInstanceOf(AnyWeekDayValue.class);
    }

    @Test
    void shouldCreateSingleNumberWeekDayValue() {
        var result = WeekDayValue.create("5");

        assertThat(result).isInstanceOf(SingleNumberWeekDayValue.class);
    }

    @Test
    void shouldCreateTextWeekDayValue() {
        var result = WeekDayValue.create("MON");

        assertThat(result).isInstanceOf(SingleTextWeekDayValue.class);
    }
}
