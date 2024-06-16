package org.cep.tokenizer;

import java.util.List;

public class Tokenizer {
    public List<String> tokenize(String input) {
        return List.of(input.split("\\s+"));
    }
}
