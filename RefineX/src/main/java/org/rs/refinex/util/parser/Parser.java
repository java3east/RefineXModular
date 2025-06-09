package org.rs.refinex.util.parser;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class Parser<T> {
    public record Result<T>(boolean ok, int endIndex, Token<T>[] values) {
        @Override
        public @NotNull String toString() {
            return "Result[" +
                    "endIndex=" + endIndex +
                    ", ok=" + ok +
                    ", values=" + Arrays.toString(values) +
                    ']';
        }
    }
    public record Token<T>(String type, T value) {
        @Override
        public @NotNull String toString() {
            return "Token[" +
                    "type='" + type + '\'' +
                    ", value='" + value + "'" +
                    ']';
        }
    }

    protected abstract Result<T> parseInternal(String input, int startIndex);

    public Parser<Object> object() {
        return new Parser<>() {
            @Override
            protected Result<Object> parseInternal(String input, int startIndex) {
                Result<?> result = Parser.this.parseInternal(input, startIndex);
                if (!result.ok())
                    return new Result<Object>(false, startIndex, new Token[0]);
                Token<?>[] tokens = result.values();
                Token<Object>[] objectTokens = new Token[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    objectTokens[i] = new Token<>(tokens[i].type(), (Object) tokens[i].value());
                }
                return new Result<>(true, result.endIndex(), objectTokens);
            }
        };
    }

    public Result<T> parse(String input, int startIndex) {
        if (input == null || startIndex < 0 || startIndex >= input.length()) {
            throw new IllegalArgumentException("Invalid input or start index");
        }
        return parseInternal(input, startIndex);
    }

    public Result<T> parse(String input) {
        return parse(input, 0);
    }
}
