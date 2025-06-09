package org.rs.refinex.util.parser;

public class BetweenParser<T> extends Parser<T> {
    private final Parser<?> before;
    private final Parser<T> between;
    private final Parser<?> after;

    public BetweenParser(Parser<?> before, Parser<T> between, Parser<?> after) {
        this.before = before;
        this.between = between;
        this.after = after;
    }

    @Override
    protected Result<T> parseInternal(String input, int startIndex) {
        Result<?> r = before.parse(input, startIndex);
        if (!r.ok())
            return new Result<T>(false, startIndex, new Token[0]);

        Result<T> betweenResult = between.parse(input, r.endIndex());
        if (!betweenResult.ok())
            return new Result<T>(false, startIndex, new Token[0]);

        Result<?> afterResult = after.parse(input, betweenResult.endIndex());
        if (!afterResult.ok())
            return new Result<T>(false, startIndex, new Token[0]);

        return new Result<T>(true, startIndex, betweenResult.values());
    }
}
