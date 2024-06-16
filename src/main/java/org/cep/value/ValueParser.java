package org.cep.value;

import java.util.ArrayList;

import static org.cep.value.ListValue.LIST_SEPARATOR;
import static org.cep.value.RangeValue.RANGE_SEPARATOR;
import static org.cep.value.StepValue.STEP_SEPARATOR;

public class ValueParser {
    public Value parse(String token, ValueFactory factory) {
        if (isList(token)) {
            return parseList(token, factory);
        } else if (isStep(token)) {
            return parseStep(token, factory);
        } else if (isRange(token)) {
            return parseRange(token, factory);
        } else {
            return factory.create(token);
        }
    }

    private boolean isList(String token) {
        return token.contains(LIST_SEPARATOR);
    }

    private ListValue parseList(String token, ValueFactory factory) {
        var split = token.split(LIST_SEPARATOR);
        var list = new ArrayList<Value>();
        for (String element : split) {
            if (isStep(element)) {
                list.add(parseStep(element, factory));
            } else if (isRange(element)) {
                list.add(parseRange(element, factory));
            } else {
                list.add(factory.create(element));
            }
        }
        return new ListValue(list);
    }

    private boolean isStep(String token) {
        return token.contains(STEP_SEPARATOR);
    }

    private Value parseStep(String token, ValueFactory factory) {
        var index = token.indexOf(STEP_SEPARATOR);
        var left = token.substring(0, index);
        var step = token.substring(index + 1);
        if (isRange(left)) {
            return new StepValue(
                    parseRange(left, factory),
                    factory.create(step)
            );
        } else {
            return new StepValue(
                    factory.create(left),
                    factory.create(step)
            );
        }
    }

    private Value parseRange(String token, ValueFactory factory) {
        var index = token.indexOf(RANGE_SEPARATOR);
        var left = token.substring(0, index);
        var right = token.substring(index + 1);
        return new RangeValue(
                factory.create(left),
                factory.create(right)
        );
    }

    private boolean isRange(String token) {
        return token.contains(RANGE_SEPARATOR);
    }
}
