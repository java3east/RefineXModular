package org.rs.refinex.toml.parser;

import org.rs.refinex.toml.KVP;
import org.rs.refinex.util.parser.*;

public class TomlKVPParser extends Parser<KVP> {
    @Override
    protected Result<KVP> parseInternal(String input, int startIndex) {
        Result<Object> key = new AnyParser(
            new TomlStringParser().object(),
            new StringParser("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_").object()
        ).parse(input, startIndex);

        if (!key.ok())
            return new Result<KVP>(false, startIndex, new Token[0]);

        Result<?> ws = new ManyParser<>(
                new AnyParser(
                     new CharParser(' ').object(),
                     new CharParser('\t').object()
                ), true
        ).parse(input, key.endIndex());

        Result<?> equals = new CharParser('=').parse(input, ws.endIndex());

        ws = new ManyParser<>(
                new AnyParser(
                        new CharParser(' ').object(),
                        new CharParser('\t').object()
                ), true
        ).parse(input, equals.endIndex());

        Result<Object> value = new AnyParser(
                new TomlStringParser().object()
        ).parse(input, ws.endIndex());

        if (!value.ok())
            return new Result<KVP>(false, startIndex, new Token[0]);

        String keyName = (String) key.values()[0].value();
        Object val = value.values()[0];

        ws = new ManyParser<>(
                new AnyParser(
                        new CharParser(' ').object(),
                        new CharParser('\t').object()
                ), true
        ).parse(input, value.endIndex());

        Result<?> eol = new EOLParser().parse(input, ws.endIndex());
        if (!eol.ok())
            return new Result<KVP>(false, startIndex, new Token[0]);

        return new Result<KVP>(true, eol.endIndex(), new Token[] {
                new Token<>("KVP", new KVP(keyName, val))
        });
    }
}
