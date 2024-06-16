package org.cep.printer;

public class SystemMessagePrinter implements MessagePrinter {
    @Override
    public void println(String message) {
        System.out.println(message);
    }
}
