package org.cep;

import org.cep.expression.ExpressionParser;
import org.cep.printer.MessagePrinter;
import org.cep.printer.SystemMessagePrinter;
import org.cep.tokenizer.Tokenizer;
import org.cep.value.ValueParser;

public class App {
    MessagePrinter messagePrinter = new SystemMessagePrinter();
    Tokenizer tokenizer = new Tokenizer();
    ValueParser valueParser = new ValueParser();
    ExpressionParser expressionParser = new ExpressionParser(valueParser);

    public static void main(String[] args) {
        var app = new App();
        app.run(args);
    }

    public void run(String[] args) {
        if (args.length != 1) {
            messagePrinter.println("cep requires only one argument\nusage: cep \"<cron expression>\"");
            return;
        }

        var rawExpression = args[0];
        var tokens = tokenizer.tokenize(rawExpression);
        if (tokens.size() != 6) {
            messagePrinter.println(
                    rawExpression
                            + " <- Invalid cron expression."
                            + "\nThe expression should contain five values of minutes, hours, day of the month, month, day of the week and the expression to call."
                            + "\nExample: */15 0 1,15 * 1-5 /usr/bin/find"
            );
            return;
        }

        var expression = expressionParser.parse(tokens);
        if (expression.isValid()) {
            var description = expression.describe();
            messagePrinter.println(description);
        } else {
            var error = expression.describeError();
            messagePrinter.println(error);
        }
    }
}
