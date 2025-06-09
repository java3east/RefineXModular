package org.rs.refinex.util.parser;

import java.util.List;
import java.util.stream.Collectors;

public class StringParser extends Parser<String> {
    private final List<Character> allowed;

    public StringParser(String allowed) {
        this.allowed = allowed.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }

    @Override
    protected Result<String> parseInternal(String input, int startIndex) {
        StringBuilder sb = new StringBuilder();
        int current = startIndex;
        while (current < input.length()) {
            if (!allowed.contains(input.charAt(current))) {
                break;
            }
            sb.append(input.charAt(current));
            current++;
        }

        return new Result<String>(true, current, new Token[]{new Token<String>("STRING", sb.toString())});
    }
}
