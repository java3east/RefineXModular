package org.rs.refinex.util.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceParser extends Parser<Object> {
    private final Parser<?>[] parsers;

    public SequenceParser(Parser<?> ...parsers) {
        this.parsers = parsers;
    }

    @Override
    protected Result<Object> parseInternal(String input, final int startIndex) {
        List<Token<Object>> tokens = new ArrayList<>();
        int index = startIndex;
        for (Parser<?> parser : parsers) {
            Result<?> result = parser.parse(input, index);
            if (!result.ok()) {
                return new Result<Object>(false, startIndex, new Token[0]);
            }
            index = result.endIndex();
            for (Token<?> token : result.values()) {
                tokens.add(new Token<>(token.type(), token.value()));
            }
        }
        return new Result<Object>(true, index, tokens.toArray(new Token[0]));
    }
}
