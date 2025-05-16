package org.rs.refinex.util;

import java.io.File;

public class FileUtils {
    public static File currentDirectory() {
        return new File(System.getProperty("user.dir"));
    }

    public static File jarDirectory() {
        String jarPath = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(jarPath).getParentFile();
    }

    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static String getExtension(String path) {
        int lastIndex = path.lastIndexOf('.');
        if (lastIndex == -1) {
            return "";
        }
        return path.substring(lastIndex + 1);
    }
}
