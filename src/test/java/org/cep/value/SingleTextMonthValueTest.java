package org.cep.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cep.value.MockedValue.mockValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleTextMonthValueTest {

    @Test
    void shouldGetRawValue() {
        var value = new SingleTextMonthValue("JAN");

        assertThat(value.getRawValue()).isEqualTo("JAN");
    }

    @Test
    void shouldGetType() {
        var value = new SingleTextMonthValue("JAN");

        assertThat(value.getType()).isEqualTo(ValueType.SINGLE_TEXT_MONTH);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            JAN | 1
            FEB | 2
            MAR | 3
            APR | 4
            MAY | 5
            JUN | 6
            JUL | 7
            AUG | 8
            SEP | 9
            OCT | 10
            NOV | 11
            DEC | 12
            """)
    void shouldDescribe(String rawValue, int expected) {
        var value = new SingleTextMonthValue(rawValue);

        assertThat(value.describe()).containsExactlyElementsOf(List.of(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC",
            "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"
    })
    void shouldBeValid(String rawValue) {
        var value = new SingleTextMonthValue(rawValue);

        assertThat(value.isValid()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"June", "december", "1", "*"})
    void shouldBeInvalid(String rawValue) {
        var value = new SingleTextMonthValue(rawValue);

        assertThat(value.isValid()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "asd", "*"})
    void shouldDescribeError(String rawValue) {
        var value = new SingleTextMonthValue(rawValue);

        assertThat(value.describeError()).isEqualTo(rawValue + " <- invalid value");
    }

    @Test
    void shouldNotBeAny() {
        var value = new SingleTextMonthValue("*");

        assertThat(value.isAny()).isFalse();
    }

    @Test
    void shouldBeSingle() {
        var value = new SingleTextMonthValue("JAN");

        assertThat(value.isSingle()).isTrue();
    }

    @Test
    void shouldHaveType() {
        var value = new SingleTextMonthValue("JAN");

        var result = value.hasType(ValueType.SINGLE_TEXT_MONTH);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var value = new SingleTextMonthValue("*");

        var result = value.hasType(ValueType.SINGLE_NUMBER_MONTH);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var mockedValue = mockValue().type(ValueType.SINGLE_NUMBER_MONTH);
        var value = new SingleTextMonthValue("OCT");

        assertThrows(NotComparableException.class, () -> value.compareTo(mockedValue));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            JAN | DEC | -1
            OCT | JUN |  1
            MAY | MAY |  0
            """)
    void shouldComperedWithOther(String first, String second, int expected) {
        var firstValue = new SingleTextMonthValue(first);
        var secondValue = new SingleTextMonthValue(second);

        var result = firstValue.compareTo(secondValue);

        assertThat(result).isEqualTo(expected);
    }
}