package org.cep.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cep.value.MockedValue.mockValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RangeValueTest {
    @Test
    void shouldGetRawValue() {
        var left = mockValue().rawValue("10");
        var right = mockValue().rawValue("20");
        var value = new RangeValue(left, right);

        var result = value.getRawValue();

        assertThat(result).isEqualTo("10-20");
    }

    @Test
    void shouldGetType() {
        var left = mockValue().rawValue("10");
        var right = mockValue().rawValue("20");
        var value = new RangeValue(left, right);

        assertThat(value.getType()).isEqualTo(ValueType.RANGE);
    }

    static Stream<Arguments> shouldDescribeSource() {
        return Stream.of(
                Arguments.of(
                        mockValue().describe(1),
                        mockValue().describe(2),
                        List.of(1, 2)
                ),
                Arguments.of(
                        mockValue().describe(5),
                        mockValue().describe(5),
                        List.of(5)
                ),
                Arguments.of(
                        mockValue().describe(3),
                        mockValue().describe(8),
                        List.of(3, 4, 5, 6, 7, 8)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldDescribeSource")
    void shouldDescribe(Value left, Value right, List<Integer> expected) {
        var value = new RangeValue(left, right);

        assertThat(value.describe()).containsExactlyElementsOf(expected);
    }

    static Stream<Arguments> shouldBeValidSource() {
        return Stream.of(
                Arguments.of(
                        mockValue().describe(1).valid(true).type(ValueType.SINGLE_NUMBER_MONTH),
                        mockValue().describe(2).valid(true).type(ValueType.SINGLE_NUMBER_MONTH)
                ),
                Arguments.of(
                        mockValue().describe(4).valid(true).type(ValueType.SINGLE_MINUTE),
                        mockValue().describe(10).valid(true).type(ValueType.SINGLE_MINUTE)
                ),
                Arguments.of(
                        mockValue().describe(2).valid(true).type(ValueType.SINGLE_HOUR),
                        mockValue().describe(2).valid(true).type(ValueType.SINGLE_HOUR)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldBeValidSource")
    void shouldBeValid(Value left, Value right) {
        var value = new RangeValue(left, right);

        assertThat(value.isValid()).isTrue();
    }

    static Stream<Arguments> shouldBeInvalidSource() {
        return Stream.of(
                Arguments.of(
                        mockValue().describe(2).valid(true).type(ValueType.SINGLE_NUMBER_MONTH),
                        mockValue().describe(1).valid(true).type(ValueType.SINGLE_NUMBER_MONTH)
                ),
                Arguments.of(
                        mockValue().describe(4).valid(true).type(ValueType.SINGLE_TEXT_MONTH),
                        mockValue().describe(10).valid(true).type(ValueType.SINGLE_NUMBER_MONTH)
                ),
                Arguments.of(
                        mockValue().describe(2).valid(true).type(ValueType.SINGLE_HOUR),
                        mockValue().describe(2).valid(false).type(ValueType.SINGLE_HOUR)
                ),
                Arguments.of(
                        mockValue().describe(2).valid(false).type(ValueType.SINGLE_HOUR),
                        mockValue().describe(2).valid(true).type(ValueType.SINGLE_HOUR)
                ),
                Arguments.of(
                        mockValue().describe(2).valid(false).type(ValueType.SINGLE_HOUR),
                        mockValue().describe(2).valid(false).type(ValueType.SINGLE_HOUR)
                ),
                Arguments.of(
                        mockValue().describe(2).valid(true).type(ValueType.ANY_MONTH),
                        mockValue().describe(6).valid(true).type(ValueType.ANY_MONTH)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldBeInvalidSource")
    void shouldBeInvalid(Value left, Value right) {
        var value = new RangeValue(left, right);

        assertThat(value.isValid()).isFalse();
    }

    static Stream<Arguments> shouldDescribeAnyErrorSource() {
        return Stream.of(
                Arguments.of(
                        mockValue().rawValue("*").valid(true).type(ValueType.ANY_WEEK_DAY),
                        mockValue().rawValue("2").valid(true).type(ValueType.SINGLE_NUMBER_MONTH),
                        "*-2"
                ),
                Arguments.of(
                        mockValue().rawValue("1").valid(true).type(ValueType.SINGLE_NUMBER_MONTH),
                        mockValue().rawValue("*").valid(true).type(ValueType.ANY_WEEK_DAY),
                        "1-*"
                ),
                Arguments.of(
                        mockValue().rawValue("*").valid(true).type(ValueType.ANY_WEEK_DAY),
                        mockValue().rawValue("*").valid(true).type(ValueType.ANY_WEEK_DAY),
                        "*-*"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldDescribeAnyErrorSource")
    void shouldDescribeAnyError(Value left, Value right, String messageStart) {
        var value = new RangeValue(left, right);

        assertThat(value.describeError()).isEqualTo(messageStart + " <- is not allowed to use * in range values");
    }

    static Stream<Arguments> shouldDescribeTypeErrorSource() {
        return Stream.of(
                Arguments.of(
                        mockValue().rawValue("1").valid(true).type(ValueType.SINGLE_TEXT_MONTH),
                        mockValue().rawValue("2").valid(true).type(ValueType.SINGLE_NUMBER_MONTH)
                ),
                Arguments.of(
                        mockValue().rawValue("1").valid(true).type(ValueType.SINGLE_NUMBER_MONTH),
                        mockValue().rawValue("2").valid(true).type(ValueType.SINGLE_TEXT_MONTH)
                ),
                Arguments.of(
                        mockValue().rawValue("1").valid(true).type(ValueType.SINGLE_HOUR),
                        mockValue().rawValue("2").valid(true).type(ValueType.SINGLE_MONTH_DAY)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldDescribeTypeErrorSource")
    void shouldDescribeTypeError(Value left, Value right) {
        var value = new RangeValue(left, right);

        assertThat(value.describeError()).isEqualTo("1-2 <- from range value should have same type as to value");
    }

    static Stream<Arguments> shouldDescribeLeftInvalidErrorSource() {
        return Stream.of(
                Arguments.of(
                        mockValue().rawValue("100").describe(100).valid(false).type(ValueType.SINGLE_TEXT_MONTH),
                        mockValue().rawValue("2").describe(2).valid(true).type(ValueType.SINGLE_TEXT_MONTH),
                        "100-2 <- from range value is invalid"
                ),
                Arguments.of(
                        mockValue().rawValue("1").describe(1).valid(true).type(ValueType.SINGLE_HOUR),
                        mockValue().rawValue("100").describe(100).valid(false).type(ValueType.SINGLE_HOUR),
                        "1-100 <- to range value is invalid"
                ),
                Arguments.of(
                        mockValue().rawValue("100").describe(100).valid(false).type(ValueType.SINGLE_NUMBER_WEEK_DAY),
                        mockValue().rawValue("100").describe(100).valid(false).type(ValueType.SINGLE_NUMBER_WEEK_DAY),
                        "100-100 <- both range values are invalid values"
                ),
                Arguments.of(
                        mockValue().rawValue("2").describe(2).valid(true).type(ValueType.SINGLE_MINUTE),
                        mockValue().rawValue("1").describe(1).valid(true).type(ValueType.SINGLE_MINUTE),
                        "2-1 <- from range value should be smaller that to range value"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldDescribeLeftInvalidErrorSource")
    void shouldDescribeLeftInvalidError(Value left, Value right, String message) {
        var value = new RangeValue(left, right);

        assertThat(value.describeError()).isEqualTo(message);
    }

    @Test
    void shouldNotBeAny() {
        var value = new RangeValue(mockValue(), mockValue());
        assertThat(value.isAny()).isFalse();
    }

    @Test
    void shouldNotBeSingle() {
        var value = new RangeValue(mockValue(), mockValue());
        assertThat(value.isSingle()).isFalse();
    }

    @Test
    void shouldHaveType() {
        var value = new RangeValue(mockValue(), mockValue());

        var result = value.hasType(ValueType.RANGE);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var value = new RangeValue(mockValue(), mockValue());

        var result = value.hasType(ValueType.ANY_MONTH_DAY);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var value = new RangeValue(mockValue(), mockValue());
        var other = mockValue().type(ValueType.RANGE);

        assertThrows(NotComparableException.class, () -> value.compareTo(other));
    }
}