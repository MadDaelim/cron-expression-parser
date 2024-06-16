package org.cep.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cep.value.MockedValue.mockValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleMinuteValueTest {

    @Test
    void shouldGetRawValue() {
        var value = new SingleMinuteValue("1");

        assertThat(value.getRawValue()).isEqualTo("1");
    }

    @Test
    void shouldGetType() {
        var value = new SingleMinuteValue("1");

        assertThat(value.getType()).isEqualTo(ValueType.SINGLE_MINUTE);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            0  | 0
            29 | 29
            59 | 59
            """)
    void shouldDescribe(String rawValue, int expected) {
        var value = new SingleMinuteValue(rawValue);

        assertThat(value.describe()).containsExactlyElementsOf(List.of(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "29", "59"})
    void shouldBeValid(String rawValue) {
        var value = new SingleMinuteValue(rawValue);

        assertThat(value.isValid()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "60", "a", "*"})
    void shouldBeInvalid(String rawValue) {
        var value = new SingleMinuteValue(rawValue);

        assertThat(value.isValid()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "asd", "*"})
    void shouldDescribeError(String rawValue) {
        var value = new SingleMinuteValue(rawValue);

        assertThat(value.describeError()).isEqualTo(rawValue + " <- invalid value");
    }

    @Test
    void shouldNotBeAny() {
        var value = new SingleMinuteValue("*");

        assertThat(value.isAny()).isFalse();
    }

    @Test
    void shouldBeSingle() {
        var value = new SingleMinuteValue("1");

        assertThat(value.isSingle()).isTrue();
    }

    @Test
    void shouldHaveType() {
        var value = new SingleMinuteValue("1");

        var result = value.hasType(ValueType.SINGLE_MINUTE);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var value = new SingleMinuteValue("*");

        var result = value.hasType(ValueType.ANY_MINUTE);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var mockedValue = mockValue().type(ValueType.ANY_MINUTE);
        var value = new SingleMinuteValue("1");

        assertThrows(NotComparableException.class, () -> value.compareTo(mockedValue));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            25 | 40 | -1
            34 | 12 |  1
            10 | 10 |  0
            """)
    void shouldComperedWithOther(String first, String second, int expected) {
        var firstValue = new SingleMinuteValue(first);
        var secondValue = new SingleMinuteValue(second);

        var result = firstValue.compareTo(secondValue);

        assertThat(result).isEqualTo(expected);
    }
}