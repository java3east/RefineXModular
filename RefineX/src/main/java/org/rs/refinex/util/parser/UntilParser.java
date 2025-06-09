package org.rs.refinex.util.parser;

public class UntilParser extends Parser<String> {
    private final Parser<?> parser;

    public UntilParser(Parser<?> parser) {
        this.parser = parser;
    }

    @Override
    protected Result<String> parseInternal(String input, int startIndex) {
        StringBuilder sb = new StringBuilder();
        int current = startIndex;
        while (current < input.length()) {
            if (this.parser.parse(input, current).ok()) {
                return new Result<String>(true, current + 1, new Token[]{new Token<>("STRING", sb.toString())});
            }
            sb.append(input.charAt(current++));
        }
        return new Result<String>(false, startIndex, new Token[0]);
    }
}
