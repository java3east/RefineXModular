package org.rs.refinex.util.parser;

import java.util.ArrayList;
import java.util.List;

public class ManyParser<T> extends Parser<T> {
    public final Parser<T> parser;
    private final boolean optional;

    public ManyParser(Parser<T> parser, boolean optional) {
        this.parser = parser;
        this.optional = optional;
    }

    public ManyParser(Parser<T> parser) {
        this(parser, false);
    }

    @Override
    protected Result<T> parseInternal(String input, int startIndex) {
        int currentIndex = startIndex;
        List<Token<T>> tokens = new ArrayList<>();

        while (currentIndex < input.length()) {
            Result<T> result = parser.parse(input, currentIndex);
            if (!result.ok()) {
                break;
            }
            for (Token<T> token : result.values()) {
                tokens.add(new Token<>(token.type(), token.value()));
            }
            currentIndex = result.endIndex();
        }

        return new Result<T>(!tokens.isEmpty() || this.optional, currentIndex, tokens.toArray(new Token[0]));
    }
}
