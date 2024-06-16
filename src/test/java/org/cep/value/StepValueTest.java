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

class StepValueTest {
    @Test
    void shouldGetRawValue() {
        var left = mockValue().rawValue("*");
        var step = mockValue().rawValue("20");
        var value = new StepValue(left, step);

        var result = value.getRawValue();

        assertThat(result).isEqualTo("*/20");
    }

    @Test
    void shouldGetType() {
        var left = mockValue().rawValue("*");
        var step = mockValue().rawValue("20");
        var value = new StepValue(left, step);

        assertThat(value.getType()).isEqualTo(ValueType.STEP);
    }

    static Stream<Arguments> shouldDescribeSource() {
        return Stream.of(
                Arguments.of(
                        mockValue().describe(1, 2, 3, 4),
                        mockValue().describe(2),
                        List.of(2, 4)
                ),
                Arguments.of(
                        mockValue().describe(4),
                        mockValue().describe(5),
                        List.of()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldDescribeSource")
    void shouldDescribe(Value left, Value step, List<Integer> expected) {
        var value = new StepValue(left, step);

        assertThat(value.describe()).containsExactlyElementsOf(expected);
    }

    static Stream<Arguments> shouldBeValidSource() {
        return Stream.of(
                Arguments.of(
                        mockValue().describe(1, 2, 3, 4).valid(true).type(ValueType.RANGE),
                        mockValue().describe(2).valid(true).type(ValueType.SINGLE_NUMBER_MONTH)
                ),
                Arguments.of(
                        mockValue().describe(4).valid(true).type(ValueType.ANY_MINUTE),
                        mockValue().describe(10).valid(true).type(ValueType.SINGLE_MINUTE)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldBeValidSource")
    void shouldBeValid(Value left, Value step) {
        var value = new StepValue(left, step);

        assertThat(value.isValid()).isTrue();
    }

    static Stream<Arguments> shouldBeInvalidSource() {
        return Stream.of(
                Arguments.of(
                        mockValue().describe(2).valid(true).type(ValueType.SINGLE_NUMBER_MONTH),
                        mockValue().describe(1).valid(true).type(ValueType.SINGLE_NUMBER_MONTH)
                ),
                Arguments.of(
                        mockValue().describe(4).valid(true).type(ValueType.RANGE),
                        mockValue().describe(10).valid(true).type(ValueType.ANY_MINUTE)
                ),
                Arguments.of(
                        mockValue().describe(2).valid(true).type(ValueType.RANGE),
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

    static Stream<Arguments> shouldDescribeErrorSource() {
        return Stream.of(
                Arguments.of(
                        mockValue().rawValue("1").valid(true).type(ValueType.SINGLE_TEXT_MONTH),
                        mockValue().rawValue("2").valid(true).type(ValueType.SINGLE_TEXT_MONTH),
                        "1/2 <- is not allowed to use single values as divider value"
                ),
                Arguments.of(
                        mockValue().rawValue("1").valid(true).type(ValueType.RANGE),
                        mockValue().rawValue("*").valid(true).type(ValueType.ANY_MONTH),
                        "1/* <- is not allowed to use * in as divisor value"
                ),
                Arguments.of(
                        mockValue().rawValue("1").valid(true).type(ValueType.SINGLE_TEXT_MONTH),
                        mockValue().rawValue("*").valid(true).type(ValueType.ANY_MONTH),
                        "1/* <- is not allowed to use single values as divider value, is not allowed to use * in as divisor value"
                ),
                Arguments.of(
                        mockValue().rawValue("10-2").valid(false).type(ValueType.RANGE),
                        mockValue().rawValue("2").valid(true).type(ValueType.SINGLE_MINUTE),
                        "10-2/2 <- divider value is invalid"
                ),
                Arguments.of(
                        mockValue().rawValue("2-6").valid(true).type(ValueType.RANGE),
                        mockValue().rawValue("100").valid(false).type(ValueType.SINGLE_MINUTE),
                        "2-6/100 <- divisor value is invalid"
                ),
                Arguments.of(
                        mockValue().rawValue("100-6").valid(false).type(ValueType.RANGE),
                        mockValue().rawValue("100").valid(false).type(ValueType.SINGLE_MINUTE),
                        "100-6/100 <- divider value is invalid, divisor value is invalid"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldDescribeErrorSource")
    void shouldDescribeError(Value divider, Value divisor, String message) {
        var value = new StepValue(divider, divisor);

        assertThat(value.describeError()).isEqualTo(message);
    }

    @Test
    void shouldNotBeAny() {
        var value = new StepValue(mockValue(), mockValue());
        assertThat(value.isAny()).isFalse();
    }

    @Test
    void shouldNotBeSingle() {
        var value = new StepValue(mockValue(), mockValue());
        assertThat(value.isSingle()).isFalse();
    }

    @Test
    void shouldHaveType() {
        var value = new StepValue(mockValue(), mockValue());

        var result = value.hasType(ValueType.STEP);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var value = new StepValue(mockValue(), mockValue());

        var result = value.hasType(ValueType.ANY_MONTH_DAY);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var value = new StepValue(mockValue(), mockValue());
        var other = mockValue().type(ValueType.STEP);

        assertThrows(NotComparableException.class, () -> value.compareTo(other));
    }

}