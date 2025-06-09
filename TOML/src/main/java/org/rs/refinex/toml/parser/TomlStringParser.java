package org.rs.refinex.toml.parser;

import org.rs.refinex.util.parser.BetweenParser;
import org.rs.refinex.util.parser.CharParser;
import org.rs.refinex.util.parser.Parser;
import org.rs.refinex.util.parser.UntilParser;

public class TomlStringParser extends Parser<String> {
    @Override
    protected Result<String> parseInternal(String input, int startIndex) {
        return new BetweenParser<>(
            new CharParser('"'),
            new UntilParser(new CharParser('"')),
            new CharParser('"')
        ).parse(input, startIndex);
    }
}
