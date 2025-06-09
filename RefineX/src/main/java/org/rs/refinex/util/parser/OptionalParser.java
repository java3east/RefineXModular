package org.rs.refinex.util.parser;

public class OptionalParser<T> extends Parser<T> {
    private final Parser<T> parser;

    public OptionalParser(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    protected Result<T> parseInternal(String input, int startIndex) {
        Result<T> result = parser.parse(input, startIndex);
        if (result.ok())
            return result;
        return new Result<T>(true, startIndex, new Token[0]);
    }
}
