package org.rs.refinex.util.parser;

public class AnyParser extends Parser<Object> {
    private final Parser<Object>[] parsers;

    public AnyParser(Parser<Object>... parsers) {
        this.parsers = parsers;
    }

    @Override
    protected Result<Object> parseInternal(String input, int startIndex) {
        for (Parser<Object> parser : parsers) {
            Result<Object> result = parser.parse(input, startIndex);
            if (result.ok()) {
                return new Result<>(true, result.endIndex(), result.values());
            }
        }
        return new Result<Object>(false, startIndex, new Token[0]);
    }
}
