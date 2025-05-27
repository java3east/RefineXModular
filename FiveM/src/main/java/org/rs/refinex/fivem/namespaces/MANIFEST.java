package org.rs.refinex.fivem.namespaces;

import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.fivem.Fxmanifest;
import org.rs.refinex.scripting.Environment;

/**
 * Namespace for the FiveM Fxmanifest.
 */
public class MANIFEST extends Namespace {
    @Native
    public static void fx_version(Environment environment, String version) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("fx_version", version);
    }

    @Native
    public static void name(Environment environment, String name) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("name", name);
    }

    @Native
    public static void game(Environment environment, String game) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("game", game);
    }

    @Native
    public static void author(Environment environment, String author) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("author", author);
    }

    @Native
    public static void description(Environment environment, String description) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("description", description);
    }

    @Native
    public static void version(Environment environment, String version) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("version", version);
    }

    @Native
    public static void client_script(Environment environment, String script) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("client_scripts", script);
    }

    @Native
    public static void client_scripts(Environment environment, String[] scripts) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("client_scripts", scripts);
    }

    @Native
    public static void server_script(Environment environment, String script) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("server_scripts", script);
    }

    @Native
    public static void server_scripts(Environment environment, String[] scripts) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("server_scripts", scripts);
    }

    @Native
    public static void shared_script(Environment environment, String script) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("shared_scripts", script);
    }

    @Native
    public static void shared_scripts(Environment environment, String[] scripts) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("shared_scripts", scripts);
    }

    @Native
    public static void file(Environment environment, String file) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("files", file);
    }

    @Native
    public static void ui_page(Environment environment, String page) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("ui_page", page);
    }

    @Native
    public static void exports(Environment environment, String[] exports) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("exports", exports);
    }

    @Native
    public static void server_exports(Environment environment, String[] exports) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("server_exports", exports);
    }

    @Native
    public static void dependency(Environment environment, String dependency) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("dependencies", dependency);
    }

    @Native
    public static void provide(Environment environment, String provide) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("provides", provide);
    }

    @Native
    public static void lua54(Environment environment, String lua54) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("lua54", lua54);
    }

    @Native
    public static void clr_disable_task_scheduler(Environment environment, String disable) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("clr_disable_task_scheduler", disable);
    }

    @Native
    public static void use_fxv2_oal(Environment environment, String use) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("use_fxv2_oal", use);
    }

    @Native
    public static void escrow_ignore(Environment environment, String[] ignore) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("escrow_ignore", ignore);
    }

    @Native
    public static void encrypted(Environment environment, String encrypted) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("encrypted", encrypted);
    }

    @Native
    public static void this_is_a_map(Environment environment, String map) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("this_is_a_map", map);
    }

    @Native
    public static void map(Environment environment, String map) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("map", map);
    }

    @Native
    public static void loadscreen(Environment environment, String loadscreen) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("loadscreen", loadscreen);
    }

    @Native
    public static void repository(Environment environment, String repository) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("repository", repository);
    }

    @Native
    public static void convar_category(Environment environment, String category) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("convar_category", category);
    }

    @Native
    public static void before_level_meta(Environment environment, String before) {
        Fxmanifest fxmanifest = (Fxmanifest) environment.getSimulator();
        fxmanifest.set("before_level_meta", before);
    }
}
