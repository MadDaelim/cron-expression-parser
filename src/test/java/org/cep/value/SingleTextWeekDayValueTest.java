package org.cep.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cep.value.MockedValue.mockValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleTextWeekDayValueTest {

    @Test
    void shouldGetRawValue() {
        var value = new SingleTextWeekDayValue("THU");

        assertThat(value.getRawValue()).isEqualTo("THU");
    }

    @Test
    void shouldGetType() {
        var value = new SingleTextWeekDayValue("FRI");

        assertThat(value.getType()).isEqualTo(ValueType.SINGLE_TEXT_WEEK_DAY);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            SUN | 0
            MON | 1
            TUR | 2
            WED | 3
            THU | 4
            FRI | 5
            SAT | 6
            """)
    void shouldDescribe(String rawValue, int expected) {
        var value = new SingleTextWeekDayValue(rawValue);

        assertThat(value.describe()).containsExactlyElementsOf(List.of(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "SUN", "MON", "TUR", "WED", "THU", "FRI", "SAT",
            "sun", "mon", "tur", "wed", "thu", "fri", "sat"
    })
    void shouldBeValid(String rawValue) {
        var value = new SingleTextWeekDayValue(rawValue);

        assertThat(value.isValid()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"monday", "Wednesday", "1", "*"})
    void shouldBeInvalid(String rawValue) {
        var value = new SingleTextWeekDayValue(rawValue);

        assertThat(value.isValid()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "asd", "*"})
    void shouldDescribeError(String rawValue) {
        var value = new SingleTextWeekDayValue(rawValue);

        assertThat(value.describeError()).isEqualTo(rawValue + " <- invalid value");
    }

    @Test
    void shouldNotBeAny() {
        var value = new SingleTextWeekDayValue("*");

        assertThat(value.isAny()).isFalse();
    }

    @Test
    void shouldBeSingle() {
        var value = new SingleTextWeekDayValue("TUR");

        assertThat(value.isSingle()).isTrue();
    }

    @Test
    void shouldHaveType() {
        var value = new SingleTextWeekDayValue("MON");

        var result = value.hasType(ValueType.SINGLE_TEXT_WEEK_DAY);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var value = new SingleTextWeekDayValue("*");

        var result = value.hasType(ValueType.SINGLE_NUMBER_WEEK_DAY);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var mockedValue = mockValue().type(ValueType.SINGLE_NUMBER_WEEK_DAY);
        var value = new SingleTextWeekDayValue("MON");

        assertThrows(NotComparableException.class, () -> value.compareTo(mockedValue));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            TUR | SAT | -1
            FRI | MON |  1
            WED | WED |  0
            """)
    void shouldComperedWithOther(String first, String second, int expected) {
        var firstValue = new SingleTextWeekDayValue(first);
        var secondValue = new SingleTextWeekDayValue(second);

        var result = firstValue.compareTo(secondValue);

        assertThat(result).isEqualTo(expected);
    }
}