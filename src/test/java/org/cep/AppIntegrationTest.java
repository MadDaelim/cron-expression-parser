package org.cep;

import org.cep.printer.MessagePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AppIntegrationTest {

    public static final String HELP_MESSAGE = "cep requires only one argument\nusage: cep \"<cron expression>\"";
    MessagePrinter messagePrinter;
    App app;

    @BeforeEach
    void beforeEach() {
        messagePrinter = Mockito.mock(MessagePrinter.class);
        app = new App();
        app.messagePrinter = messagePrinter;
    }

    @Test
    void shouldBeValidExpression1() {
        var rawExpression = "*/15 0 1,15 * 1-5 /usr/bin/find";

        app.run(new String[]{rawExpression});

        verify(messagePrinter, times(1)).println("""
                minute         0 15 30 45
                hour           0
                day of month   1 15
                month          1 2 3 4 5 6 7 8 9 10 11 12
                day of week    1 2 3 4 5
                command        /usr/bin/find
                """);
    }

    @Test
    void shouldBeValidExpression2() {
        var rawExpression = "*/15 0 1,15 * MON-THU /usr/bin/find";

        app.run(new String[]{rawExpression});

        verify(messagePrinter, times(1)).println("""
                minute         0 15 30 45
                hour           0
                day of month   1 15
                month          1 2 3 4 5 6 7 8 9 10 11 12
                day of week    1 2 3 4
                command        /usr/bin/find
                """);
    }

    @Test
    void shouldBeValidExpression3() {
        var rawExpression = "*/15 0 1,15 JAN-OCT/2 MON-THU /usr/bin/find";

        app.run(new String[]{rawExpression});

        verify(messagePrinter, times(1)).println("""
                minute         0 15 30 45
                hour           0
                day of month   1 15
                month          2 4 6 8 10
                day of week    1 2 3 4
                command        /usr/bin/find
                """);
    }

    @Test
    void shouldBeValidExpression4() {
        var rawExpression = "*/15 0 1,15-18 JAN-OCT/2 MON-THU /usr/bin/find";

        app.run(new String[]{rawExpression});

        verify(messagePrinter, times(1)).println("""
                minute         0 15 30 45
                hour           0
                day of month   1 15 16 17 18
                month          2 4 6 8 10
                day of week    1 2 3 4
                command        /usr/bin/find
                """);
    }

    @Test
    void shouldBeValidExpression5() {
        var rawExpression = "*/15 0 1-14/3,15-18 JAN-OCT/2 MON-THU /usr/bin/find";

        app.run(new String[]{rawExpression});

        verify(messagePrinter, times(1)).println("""
                minute         0 15 30 45
                hour           0
                day of month   3 6 9 12 15 16 17 18
                month          2 4 6 8 10
                day of week    1 2 3 4
                command        /usr/bin/find
                """);
    }

    @Test
    void shouldPrintHelpMessage1() {
        app.run(new String[]{});

        verify(messagePrinter, times(1)).println(
                HELP_MESSAGE
        );
    }

    @Test
    void shouldPrintHelpMessage2() {
        app.run(new String[]{"first", "second"});

        verify(messagePrinter, times(1)).println(
                HELP_MESSAGE
        );
    }

    @Test
    void shouldPrintExpressionErrorMessage() {
        app.run(new String[]{"* * * * * * /test/bin"});

        verify(messagePrinter, times(1)).println(
                "* * * * * * /test/bin <- Invalid cron expression."
                        + "\nThe expression should contain five values of minutes, hours, day of the month, month, day of the week and the expression to call."
                        + "\nExample: */15 0 1,15 * 1-5 /usr/bin/find"
        );
    }

    @Test
    void shouldDescribeError1() {
        var rawExpression = "*/15 0 14-2/3,15-18 JAN-OCT/2 MON-THU /usr/bin/find";

        app.run(new String[]{rawExpression});

        verify(messagePrinter, times(1)).println("""
                minute         */15
                hour           0
                day of month   14-2/3 <- divider value is invalid
                               15-18
                               Day of month should be * or number 1-31
                month          JAN-OCT/2
                day of week    MON-THU
                command        /usr/bin/find
                """);
    }

    @Test
    void shouldDescribeError2() {
        var rawExpression = "*/* 0 2-5/3 JAN-OCT/2 MON-THU /usr/bin/find";

        app.run(new String[]{rawExpression});

        verify(messagePrinter, times(1)).println("""
                minute         */* <- is not allowed to use * in as divisor value
                               Minute should be * or number 0-59
                hour           0
                day of month   2-5/3
                month          JAN-OCT/2
                day of week    MON-THU
                command        /usr/bin/find
                """);
    }

    @Test
    void shouldDescribeError3() {
        var rawExpression = "*/2 24 2-5/3 JAN-OCT/2 MON-THU /usr/bin/find";

        app.run(new String[]{rawExpression});

        verify(messagePrinter, times(1)).println("""
                minute         */2
                hour           24 <- invalid value
                               Hour should be * or number 0-23
                day of month   2-5/3
                month          JAN-OCT/2
                day of week    MON-THU
                command        /usr/bin/find
                """);
    }

    @Test
    void shouldDescribeError4() {
        var rawExpression = "*/2 23 2-5/3 DEC-JAN 2/2 /usr/bin/find";

        app.run(new String[]{rawExpression});

        verify(messagePrinter, times(1)).println("""
                minute         */2
                hour           23
                day of month   2-5/3
                month          DEC-JAN <- from range value should be smaller that to range value
                               Month should be * or number 1-12 or JAN-DEC value
                day of week    2/2 <- is not allowed to use single values as divider value
                               Day of week should be * or number 0-6 or SAN-SAT value
                command        /usr/bin/find
                """);
    }
}
