package org.rs.refinex.plugin;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.language.LanguageManager;

/**
 * A language plugin can be used to add support for a new scripting language to RefineX.
 * It tells RefineX how to interact with the language and how to run scripts in that language.
 * This class should be extended by any plugin that wants to add support for a new language.
 *
 * @author Java3east
 */
public abstract class LanguagePlugin implements Plugin {
    private final String languageName;

    public LanguagePlugin(final String languageName) {
        this.languageName = languageName;
        LanguageManager.register(this);
    }

    /**
     * Returns the name of the extension that this language plugin supports.
     * @return the extension of the language
     */
    public abstract @NotNull String getExtension();

    @Override
    public @NotNull String getName() {
        return languageName;
    }

    @Override
    public String toString() {
        return "PLUGIN{language=" + languageName + "}";
    }
}
