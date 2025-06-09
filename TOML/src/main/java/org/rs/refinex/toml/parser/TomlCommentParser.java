package org.rs.refinex.toml.parser;

import org.rs.refinex.util.parser.*;

public class TomlCommentParser extends Parser<String> {
    @Override
    protected Result<String> parseInternal(String input, int startIndex) {
        Result<Object> result = new SequenceParser(
                new CharParser('#'),
                new UntilParser(new EOLParser()),
                new OptionalParser<>(
                        new EOLParser()
                )
        ).parse(input, startIndex);
        if (!result.ok())
            return new Result<String>(false, startIndex, new Token[0]);
        StringBuilder sb = new StringBuilder();
        for (Token<Object> token : result.values()) {
            sb.append(token.value());
        }
        return new Result<String>(true, result.endIndex(), new Token[]{new Token<>("COMMENT", sb.toString())});
    }
}
