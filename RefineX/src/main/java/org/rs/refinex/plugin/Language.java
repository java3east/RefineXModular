package org.rs.refinex.plugin;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.value.ValueMapper;

/**
 * A language plugin can be used to add support for a new scripting language to RefineX.
 * It tells RefineX how to interact with the language and how to run scripts in that language.
 * This class should be extended by any plugin that wants to add support for a new language.
 *
 * @author Florian B.
 */
public abstract class Language implements Plugin {
    private final String languageName;

    /**
     * Creates a new language plugin with the given name.
     * @param languageName the name of the language
     */
    public Language(final String languageName) {
        this.languageName = languageName;
        LanguageManager.register(this);
    }

    /**
     * Returns the name of the extension that this language plugin supports.
     * @return the extension of the language
     */
    public abstract @NotNull String getExtension();

    /**
     * Creates a new scripting environment for this language.
     * @return the created environment
     */
    public abstract @NotNull Environment createEnvironment(final @NotNull Simulator simulator, final @NotNull Resource resource);

    /**
     * Returns a mapper that can be used to convert types from this language to Java types.
     * @return the value mapper
     */
    public abstract @NotNull ValueMapper<?> getValueMapper();

    @Override
    public @NotNull String getName() {
        return languageName;
    }

    @Override
    public String toString() {
        return "PLUGIN{language=" + languageName + "}";
    }
}
