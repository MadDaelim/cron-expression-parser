package org.cep.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cep.value.MockedValue.mockValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleNumberWeekDayValueTest {

    @Test
    void shouldGetRawValue() {
        var value = new SingleNumberWeekDayValue("1");

        assertThat(value.getRawValue()).isEqualTo("1");
    }

    @Test
    void shouldGetType() {
        var value = new SingleNumberWeekDayValue("1");

        assertThat(value.getType()).isEqualTo(ValueType.SINGLE_NUMBER_WEEK_DAY);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            0 | 0
            3 | 3
            6 | 6
            """)
    void shouldDescribe(String rawValue, int expected) {
        var value = new SingleNumberWeekDayValue(rawValue);

        assertThat(value.describe()).containsExactlyElementsOf(List.of(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "3", "6"})
    void shouldBeValid(String rawValue) {
        var value = new SingleNumberWeekDayValue(rawValue);

        assertThat(value.isValid()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "7", "a", "*"})
    void shouldBeInvalid(String rawValue) {
        var value = new SingleNumberWeekDayValue(rawValue);

        assertThat(value.isValid()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "asd", "*"})
    void shouldDescribeError(String rawValue) {
        var value = new SingleNumberWeekDayValue(rawValue);

        assertThat(value.describeError()).isEqualTo(rawValue + " <- invalid value");
    }

    @Test
    void shouldNotBeAny() {
        var value = new SingleNumberWeekDayValue("*");

        assertThat(value.isAny()).isFalse();
    }

    @Test
    void shouldBeSingle() {
        var value = new SingleNumberWeekDayValue("1");

        assertThat(value.isSingle()).isTrue();
    }

    @Test
    void shouldHaveType() {
        var value = new SingleNumberWeekDayValue("1");

        var result = value.hasType(ValueType.SINGLE_NUMBER_WEEK_DAY);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var value = new SingleNumberWeekDayValue("*");

        var result = value.hasType(ValueType.SINGLE_TEXT_WEEK_DAY);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var mockedValue = mockValue().type(ValueType.SINGLE_TEXT_WEEK_DAY);
        var value = new SingleNumberWeekDayValue("1");

        assertThrows(NotComparableException.class, () -> value.compareTo(mockedValue));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            1 | 5 | -1
            6 | 3 |  1
            4 | 4 |  0
            """)
    void shouldComperedWithOther(String first, String second, int expected) {
        var firstValue = new SingleNumberWeekDayValue(first);
        var secondValue = new SingleNumberWeekDayValue(second);

        var result = firstValue.compareTo(secondValue);

        assertThat(result).isEqualTo(expected);
    }
}