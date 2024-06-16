package org.cep.expression;

import org.cep.value.*;

import java.util.List;

public class ExpressionParser {
    private final ValueParser valueParser;

    public ExpressionParser(ValueParser valueParser) {
        this.valueParser = valueParser;
    }

    public Expression parse(List<String> tokens) {
        var minute = valueParser.parse(tokens.get(0), MinuteValue::create);
        var hour = valueParser.parse(tokens.get(1), HourValue::create);
        var monthDay = valueParser.parse(tokens.get(2), MonthDayValue::create);
        var month = valueParser.parse(tokens.get(3), MonthValue::create);
        var weekDay = valueParser.parse(tokens.get(4), WeekDayValue::create);
        return new Expression(
                minute,
                hour,
                monthDay,
                month,
                weekDay,
                tokens.get(5)
        );
    }
}
