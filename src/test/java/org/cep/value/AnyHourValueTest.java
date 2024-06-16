package org.cep.value;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cep.value.MockedValue.mockValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnyHourValueTest {

    AnyHourValue value = new AnyHourValue();

    @Test
    void shouldGetRawValue() {
        assertThat(value.getRawValue()).isEqualTo("*");
    }

    @Test
    void shouldGetType() {
        assertThat(value.getType()).isEqualTo(ValueType.ANY_HOUR);
    }

    @Test
    void shouldDescribe() {
        assertThat(value.describe()).containsExactlyElementsOf(IntStream.range(0, 24).boxed().toList());
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
        var result = value.hasType(ValueType.ANY_HOUR);

        assertThat(result).isTrue();
    }

    @Test
    void shouldNotHaveType() {
        var result = value.hasType(ValueType.ANY_MONTH);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCompered() {
        var mockedValue = mockValue().type(ValueType.ANY_HOUR);

        assertThrows(NotComparableException.class, () -> value.compareTo(mockedValue));
    }
}
