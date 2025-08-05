package org.rs.refinex.rfx.namespaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.util.FileUtils;

/**
 * The DEPLOY namespace is responsible for handling deployment-related operations.
 * E.g. it can pack a resource into a zip file, so it can be uploaded to the git repository.
 * This feature allows to easily create builds containing all files needed for a specific deployment and
 * ignoring files that are not needed. This way tests can be ignored when deploying to production.
 */
public class DEPLOY extends Namespace {
    private static List<Path> getGlobFiles(final String path, String glob, final List<Path> files) {
        glob = new File(path).getAbsolutePath() + "/" + glob;
        @NotNull String finalGlob = glob.replace("\\", "/");
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + finalGlob);
        
        return files.stream()
                .filter(file -> matcher.matches(file))
                .toList();
    }
    
    /**
     * Packs the given folder into a zip file with the given name.
     * The files listed in the ignore list will not be included and can use globbing wildcards.
     * @param environment the scripting environment
     * @param path the path to the folder to pack
     * @param outputFile the name of the output zip file
     * @param ignore the list of files to ignore during packing, can use globbing wildcards
     */
    @Native
    public static void PACK(Environment environment, String path, String outputFile, String[] ignore) {
        List<Path> files = FileUtils.getFilesRecursive(new File(path));
        for (String glob : ignore) {
            files.removeAll(getGlobFiles(path, glob, files));
        }

        Path basePath = new File(path).toPath().toAbsolutePath();

        try (FileOutputStream fos = new FileOutputStream(outputFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (Path filePath : files) {
                File datei = filePath.toFile();

                try (FileInputStream fis = new FileInputStream(datei)) {
                    // Calculate relative path from base directory to preserve folder structure
                    Path relativePath = basePath.relativize(filePath.toAbsolutePath());
                    String zipEntryName = relativePath.toString().replace("\\", "/");
                    
                    ZipEntry zipEintrag = new ZipEntry(zipEntryName);
                    zos.putNextEntry(zipEintrag);

                    byte[] buffer = new byte[1024];
                    int laenge;
                    while ((laenge = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, laenge);
                    }

                    zos.closeEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
