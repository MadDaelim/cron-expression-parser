package org.cep.expression;

import org.cep.value.Value;

import java.util.stream.Collectors;

public class Expression {
    private final Value minute;
    private final Value hour;
    private final Value monthDay;
    private final Value month;
    private final Value weekDay;
    private final String command;

    Expression(Value minute, Value hour, Value monthDay, Value month, Value weekDay, String command) {
        this.minute = minute;
        this.hour = hour;
        this.monthDay = monthDay;
        this.month = month;
        this.weekDay = weekDay;
        this.command = command;
    }

    public boolean isValid() {
        return minute.isValid() && hour.isValid() && monthDay.isValid() && month.isValid() && weekDay.isValid();
    }

    public String describe() {
        return """
                minute         %s
                hour           %s
                day of month   %s
                month          %s
                day of week    %s
                command        %s
                """.formatted(
                describe(minute),
                describe(hour),
                describe(monthDay),
                describe(month),
                describe(weekDay),
                command
        );
    }

    private String describe(Value value) {
        return value.describe().stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    public String describeError() {
        return """
                minute         %s
                hour           %s
                day of month   %s
                month          %s
                day of week    %s
                command        %s
                """.formatted(
                describeError(minute, "Minute should be * or number 0-59"),
                describeError(hour, "Hour should be * or number 0-23"),
                describeError(monthDay, "Day of month should be * or number 1-31"),
                describeError(month, "Month should be * or number 1-12 or JAN-DEC value"),
                describeError(weekDay, "Day of week should be * or number 0-6 or SAN-SAT value"),
                command
        );
    }

    private String describeError(Value value, String validValueDescription) {
        if (value.isValid()) {
            return value.getRawValue();
        }
        var errorDescription = value.describeError();
        return errorDescription + "\n               " + validValueDescription;
    }
}
