package org.rs.refinex.util;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for file operations.
 */
public class FileUtils {
    /**
     * Returns the current working directory.
     * @return The current working directory.
     */
    public static File currentDirectory() {
        return new File(System.getProperty("user.dir"));
    }

    /**
     * Returns the directory of the jar file that contains this class.
     * @return The directory of the jar file that contains this class.
     */
    public static File jarDirectory() {
        String jarPath = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(jarPath).getParentFile();
    }

    /**
     * Checks if a file or directory exists at the given path.
     * @param path The path to check.
     * @return True if the file or directory exists, false otherwise.
     */
    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * Returns the extension of a file at the given path.
     * @param path The path to the file.
     * @return The extension of the file, or an empty string if there is no extension.
     */
    public static String getExtension(String path) {
        int lastIndex = path.lastIndexOf('.');
        if (lastIndex == -1) {
            return "";
        }
        return path.substring(lastIndex + 1);
    }

    public static @NotNull List<Path> getFilesRecursive(final @NotNull File dir) {
        List<Path> files = new ArrayList<>();
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                files.addAll(getFilesRecursive(file));
            } else {
                files.add(file.getAbsoluteFile().toPath());
            }
        }
        return files;
    }
}
