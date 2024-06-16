package org.cep.value;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cep.value.MockedValue.mockValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnyMonthDayValueTest {

    AnyMonthDayValue value = new AnyMonthDayValue();

    @Test
    void shouldGetRawValue() {
        assertThat(value.getRawValue()).isEqualTo("*");
    }

    @Test
    void shouldGetType() {
        assertThat(value.getType()).isEqualTo(ValueType.ANY_MONTH_DAY);
    }

    @Test
    void shouldDescribe() {
        assertThat(value.describe()).containsExactlyElementsOf(IntStream.range(1, 32).boxed().toList());
    }

    @Test
    void shouldBeValid() {
        assertThat(value.isValid()).isTrue();
    }

    @Test
    void shouldDescribeError() {
        assertThat(value.describeError()).isEqualTo("* <- invalid value");
    }

    @Test
    void shouldBeAny() {
        assertThat(value.isAny()).isTrue();
    }

    @Test
    void shouldNotBeSingle() {
        assertThat(value.isSingle()).isFalse();
    }

    @Test
    void shouldHaveType() {
        var result = value.hasType(ValueType.ANY_MONTH_DAY);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var result = value.hasType(ValueType.ANY_MONTH);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var mockedValue = mockValue().type(ValueType.ANY_MONTH_DAY);

        assertThrows(NotComparableException.class, () -> value.compareTo(mockedValue));
    }
}
