package org.rs.refinex.util.parser;

public class CharParser extends Parser<Character> {
    private final char c;

    public CharParser(char c) {
        this.c = c;
    }

    @Override
    protected Result<Character> parseInternal(String input, int startIndex) {
        if (input.charAt(startIndex) == c) {
            return new Result<Character>(true, startIndex + 1, new Token[]{new Token<>("char", c)});
        } else {
            return new Result<Character>(false, startIndex, new Token[0]);
        }
    }
}
