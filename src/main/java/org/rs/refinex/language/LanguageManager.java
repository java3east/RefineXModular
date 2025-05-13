package org.rs.refinex.language;

import org.rs.refinex.plugin.Language;

import java.util.HashMap;
import java.util.Optional;

/**
 * The language manager is responsible for managing the language plugins.
 * It allows for the registration and retrieval of language plugins by their name.
 * This is useful for organizing and accessing different languages in the application.
 *
 * @author Florian B.
 */
public class LanguageManager {
    private static final HashMap<String, Language> languagePlugins = new HashMap<>();

    public static void register(Language plugin) {
        languagePlugins.put(plugin.getName(), plugin);
    }

    public static Language getLanguage(final String name) {
        return languagePlugins.get(name);
    }

    public static Optional<Language> getByExtension(final String extension) {
        for (Language plugin : languagePlugins.values()) {
            if (plugin.getExtension().equalsIgnoreCase(extension)) {
                return Optional.of(plugin);
            }
        }
        return Optional.empty();
    }
}
