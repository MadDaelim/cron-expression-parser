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

class ListValueTest {
    static Stream<Arguments> shouldGetRawValueSource() {
        return Stream.of(
                Arguments.of(
                        List.of(mockValue().rawValue("t1")),
                        "t1"
                ),
                Arguments.of(
                        List.of(mockValue().rawValue("t1"), mockValue().rawValue("t2")),
                        "t1,t2"),
                Arguments.of(
                        List.of(
                                mockValue().rawValue("t1"),
                                mockValue().rawValue("t2"),
                                mockValue().rawValue("t3")
                        ),
                        "t1,t2,t3"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("shouldGetRawValueSource")
    void shouldGetRawValue(List<Value> values, String expected) {
        var value = new ListValue(values);

        var result = value.getRawValue();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldGetType() {
        List<Value> values = List.of(mockValue().rawValue("t1"));
        var value = new ListValue(values);

        assertThat(value.getType()).isEqualTo(ValueType.LIST);
    }

    static Stream<Arguments> shouldDescribeSource() {
        return Stream.of(
                Arguments.of(List.of(
                        mockValue().describe(1, 2, 3)
                ), List.of(1, 2, 3)),
                Arguments.of(List.of(
                        mockValue().describe(1, 2, 3),
                        mockValue().describe(4, 5, 6)
                ), List.of(1, 2, 3, 4, 5, 6)),
                Arguments.of(List.of(
                        mockValue().describe(1, 2),
                        mockValue().describe(2, 3),
                        mockValue().describe(3, 4, 5)
                ), List.of(1, 2, 3, 4, 5))
        );
    }

    @ParameterizedTest
    @MethodSource("shouldDescribeSource")
    void shouldDescribe(List<Value> values, List<Integer> expected) {
        var value = new ListValue(values);

        assertThat(value.describe()).containsExactlyElementsOf(expected);
    }

    static Stream<Arguments> shouldBeValidSource() {
        return Stream.of(
                Arguments.of(List.of(mockValue().valid(true))),
                Arguments.of(List.of(mockValue().valid(true), mockValue().valid(true))),
                Arguments.of(List.of(mockValue().valid(true), mockValue().valid(true), mockValue().valid(true)))
        );
    }

    @ParameterizedTest
    @MethodSource("shouldBeValidSource")
    void shouldBeValid(List<Value> values) {
        var value = new ListValue(values);

        assertThat(value.isValid()).isTrue();
    }

    static Stream<Arguments> shouldBeInvalidSource() {
        return Stream.of(
                Arguments.of(List.of(mockValue().valid(false))),
                Arguments.of(List.of(mockValue().valid(true), mockValue().valid(false))),
                Arguments.of(List.of(mockValue().valid(true), mockValue().valid(false), mockValue().valid(true))),
                Arguments.of(List.of(mockValue().valid(true), mockValue().valid(false), mockValue().valid(false))),
                Arguments.of(List.of(mockValue().valid(false), mockValue().valid(false), mockValue().valid(false)))
        );
    }

    @ParameterizedTest
    @MethodSource("shouldBeInvalidSource")
    void shouldBeInvalid(List<Value> values) {
        var value = new ListValue(values);

        assertThat(value.isValid()).isFalse();
    }

    @Test
    void shouldDescribeError() {
        List<Value> values = List.of(
                mockValue().valid(false).error("e1").rawValue("rv1"),
                mockValue().valid(true).error("e2").rawValue("rv2"),
                mockValue().valid(false).error("e3").rawValue("rv3"),
                mockValue().valid(false).error("e4").rawValue("rv4"),
                mockValue().valid(true).error("e5").rawValue("rv5")
        );
        var value = new ListValue(values);

        assertThat(value.describeError()).isEqualTo("""
                e1
                               rv2
                               e3
                               e4
                               rv5"""
        );
    }

    @Test
    void shouldNotBeAny() {
        var value = new ListValue(List.of());
        assertThat(value.isAny()).isFalse();
    }

    @Test
    void shouldNotBeSingle() {
        var value = new ListValue(List.of());
        assertThat(value.isSingle()).isFalse();
    }

    @Test
    void shouldHaveType() {
        var value = new ListValue(List.of());

        var result = value.hasType(ValueType.LIST);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var value = new ListValue(List.of());

        var result = value.hasType(ValueType.ANY_MONTH_DAY);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var value = new ListValue(List.of());
        var mockedValue = mockValue().type(ValueType.LIST);

        assertThrows(NotComparableException.class, () -> value.compareTo(mockedValue));
    }
}
