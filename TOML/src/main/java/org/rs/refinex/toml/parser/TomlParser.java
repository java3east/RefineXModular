package org.rs.refinex.toml.parser;

import org.rs.refinex.util.parser.*;

public class TomlParser extends Parser<Object> {
    @Override
    protected Result<Object> parseInternal(String input, int startIndex) {
        return new ManyParser<>(
                new AnyParser(
                    new TomlCommentParser().object(),
                    new TomlKVPParser().object()
                )
        ).parse(input, startIndex);
    }

    public static void main(String[] args) {
        // Example usage
        TomlParser parser = new TomlParser();
        String tomlInput = """
            # This is a comment
            key="my value"
            key1="my value 1"
            """;
        Parser.Result<Object> result = parser.parse(tomlInput);
        System.out.println(result);
    }
}
