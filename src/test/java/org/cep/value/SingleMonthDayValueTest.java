package org.cep.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cep.value.MockedValue.mockValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleMonthDayValueTest {

    @Test
    void shouldGetRawValue() {
        var value = new SingleMonthDayValue("1");

        assertThat(value.getRawValue()).isEqualTo("1");
    }

    @Test
    void shouldGetType() {
        var value = new SingleMonthDayValue("1");

        assertThat(value.getType()).isEqualTo(ValueType.SINGLE_MONTH_DAY);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            1  | 1
            15 | 15
            31 | 31
            """)
    void shouldDescribe(String rawValue, int expected) {
        var value = new SingleMonthDayValue(rawValue);

        assertThat(value.describe()).containsExactlyElementsOf(List.of(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "15", "31"})
    void shouldBeValid(String rawValue) {
        var value = new SingleMonthDayValue(rawValue);

        assertThat(value.isValid()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "32", "a", "*"})
    void shouldBeInvalid(String rawValue) {
        var value = new SingleMonthDayValue(rawValue);

        assertThat(value.isValid()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "asd", "*"})
    void shouldDescribeError(String rawValue) {
        var value = new SingleMonthDayValue(rawValue);

        assertThat(value.describeError()).isEqualTo(rawValue + " <- invalid value");
    }

    @Test
    void shouldNotBeAny() {
        var value = new SingleMonthDayValue("*");

        assertThat(value.isAny()).isFalse();
    }

    @Test
    void shouldBeSingle() {
        var value = new SingleMonthDayValue("1");

        assertThat(value.isSingle()).isTrue();
    }

    @Test
    void shouldHaveType() {
        var value = new SingleMonthDayValue("1");

        var result = value.hasType(ValueType.SINGLE_MONTH_DAY);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var value = new SingleMonthDayValue("*");

        var result = value.hasType(ValueType.ANY_MONTH_DAY);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var mockedValue = mockValue().type(ValueType.ANY_MONTH_DAY);
        var value = new SingleMonthDayValue("1");

        assertThrows(NotComparableException.class, () -> value.compareTo(mockedValue));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            12 | 24 | -1
            21 | 8  |  1
            11 | 11 |  0
            """)
    void shouldComperedWithOther(String first, String second, int expected) {
        var firstValue = new SingleMonthDayValue(first);
        var secondValue = new SingleMonthDayValue(second);

        var result = firstValue.compareTo(secondValue);

        assertThat(result).isEqualTo(expected);
    }
}