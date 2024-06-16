package org.cep.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cep.value.MockedValue.mockValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleNumberMonthValueTest {

    @Test
    void shouldGetRawValue() {
        var value = new SingleNumberMonthValue("1");

        assertThat(value.getRawValue()).isEqualTo("1");
    }

    @Test
    void shouldGetType() {
        var value = new SingleNumberMonthValue("1");

        assertThat(value.getType()).isEqualTo(ValueType.SINGLE_NUMBER_MONTH);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            1  | 1
            6  | 6
            12 | 12
            """)
    void shouldDescribe(String rawValue, int expected) {
        var value = new SingleNumberMonthValue(rawValue);

        assertThat(value.describe()).containsExactlyElementsOf(List.of(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "6", "12"})
    void shouldBeValid(String rawValue) {
        var value = new SingleNumberMonthValue(rawValue);

        assertThat(value.isValid()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "13", "a", "*"})
    void shouldBeInvalid(String rawValue) {
        var value = new SingleNumberMonthValue(rawValue);

        assertThat(value.isValid()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "asd", "*"})
    void shouldDescribeError(String rawValue) {
        var value = new SingleNumberMonthValue(rawValue);

        assertThat(value.describeError()).isEqualTo(rawValue + " <- invalid value");
    }

    @Test
    void shouldNotBeAny() {
        var value = new SingleNumberMonthValue("*");

        assertThat(value.isAny()).isFalse();
    }

    @Test
    void shouldBeSingle() {
        var value = new SingleNumberMonthValue("1");

        assertThat(value.isSingle()).isTrue();
    }

    @Test
    void shouldHaveType() {
        var value = new SingleNumberMonthValue("1");

        var result = value.hasType(ValueType.SINGLE_NUMBER_MONTH);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var value = new SingleNumberMonthValue("*");

        var result = value.hasType(ValueType.ANY_MONTH_DAY);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var mockedValue = mockValue().type(ValueType.SINGLE_TEXT_MONTH);
        var value = new SingleNumberMonthValue("1");

        assertThrows(NotComparableException.class, () -> value.compareTo(mockedValue));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            8  | 10 | -1
            11 | 7  |  1
            1  | 1  |  0
            """)
    void shouldComperedWithOther(String first, String second, int expected) {
        var firstValue = new SingleNumberMonthValue(first);
        var secondValue = new SingleNumberMonthValue(second);

        var result = firstValue.compareTo(secondValue);

        assertThat(result).isEqualTo(expected);
    }
}