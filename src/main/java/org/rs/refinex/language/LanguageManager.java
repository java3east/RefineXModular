package org.rs.refinex.language;

import org.rs.refinex.plugin.LanguagePlugin;

import java.util.HashMap;
import java.util.Optional;

/**
 * The language manager is responsible for managing the language plugins.
 * It allows for the registration and retrieval of language plugins by their name.
 * This is useful for organizing and accessing different languages in the application.
 *
 * @author Java3east
 */
public class LanguageManager {
    private static final HashMap<String, LanguagePlugin> languagePlugins = new HashMap<>();

    public static void register(LanguagePlugin plugin) {
        languagePlugins.put(plugin.getName(), plugin);
    }

    public static LanguagePlugin getLanguage(final String name) {
        return languagePlugins.get(name);
    }

    public static Optional<LanguagePlugin> getByExtension(final String extension) {
        for (LanguagePlugin plugin : languagePlugins.values()) {
            if (plugin.getExtension().equalsIgnoreCase(extension)) {
                return Optional.of(plugin);
            }
        }
        return Optional.empty();
    }
}
