package org.cep.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cep.value.MockedValue.mockValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleHourValueTest {

    @Test
    void shouldGetRawValue() {
        var value = new SingleHourValue("1");

        assertThat(value.getRawValue()).isEqualTo("1");
    }

    @Test
    void shouldGetType() {
        var value = new SingleHourValue("1");

        assertThat(value.getType()).isEqualTo(ValueType.SINGLE_HOUR);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            0  | 0
            12 | 12
            23 | 23
            """)
    void shouldDescribe(String rawValue, int expected) {
        var value = new SingleHourValue(rawValue);

        assertThat(value.describe()).containsExactlyElementsOf(List.of(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "12", "23"})
    void shouldBeValid(String rawValue) {
        var value = new SingleHourValue(rawValue);

        assertThat(value.isValid()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "24", "a", "*"})
    void shouldBeInvalid(String rawValue) {
        var value = new SingleHourValue(rawValue);

        assertThat(value.isValid()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "asd", "*"})
    void shouldDescribeError(String rawValue) {
        var value = new SingleHourValue(rawValue);

        assertThat(value.describeError()).isEqualTo(rawValue + " <- invalid value");
    }

    @Test
    void shouldNotBeAny() {
        var value = new SingleHourValue("*");

        assertThat(value.isAny()).isFalse();
    }

    @Test
    void shouldBeSingle() {
        var value = new SingleHourValue("1");

        assertThat(value.isSingle()).isTrue();
    }

    @Test
    void shouldHaveType() {
        var value = new SingleHourValue("1");

        var result = value.hasType(ValueType.SINGLE_HOUR);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var value = new SingleHourValue("*");

        var result = value.hasType(ValueType.ANY_HOUR);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var mockedValue = mockValue().type(ValueType.ANY_HOUR);
        var value = new SingleHourValue("1");

        assertThrows(NotComparableException.class, () -> value.compareTo(mockedValue));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            7 | 10 | -1
            8 | 2  |  1
            5 | 5  |  0
            """)
    void shouldComperedWithOther(String first, String second, int expected) {
        var firstValue = new SingleHourValue(first);
        var secondValue = new SingleHourValue(second);

        var result = firstValue.compareTo(secondValue);

        assertThat(result).isEqualTo(expected);
    }
}
