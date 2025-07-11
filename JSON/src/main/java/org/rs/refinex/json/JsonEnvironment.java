package org.rs.refinex.json;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.rs.refinex.RefineX;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.context.ContextEventHandler;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.language.LanguageManager;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;
import org.rs.refinex.simulation.Simulator;
import org.rs.refinex.util.FileUtils;
import org.rs.refinex.value.Function;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JsonEnvironment implements Environment {
    private final HashMap<String, Object> dataStore = new HashMap<>();
    private final Simulator simulator;
    private final Resource resource;

    public JsonEnvironment(Simulator simulator, Resource resource) {
        this.simulator = simulator;
        this.resource = resource;
    }

    @Override
    public Optional<Object> get(@NotNull String key) {
        if (!dataStore.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of(dataStore.get(key));
    }

    @Override
    public Object getGlobal(@NotNull String key) {
        return dataStore.getOrDefault(key, null);
    }

    @Override
    public void set(@NotNull String key, @NotNull Object value, boolean shared) {
        simulator.setData(key, value);
        this.dataStore.put(key, value);
    }

    @Override
    public void addNamespace(@NotNull Namespace namespace) { }

    @Override
    public void callSrcFunction(@NotNull String name, Object... args) {

    }

    @Override
    public void load(@NotNull String str) {
        JSONObject json = new JSONObject(str);
        Map<String, Object> map = json.toMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                for (Map.Entry<String, Object> subEntry : ((Map<String, Object>) value).entrySet()) {
                    this.set(subEntry.getKey(), subEntry.getValue(), true);
                }
            }
            this.set(key, value, true);
        }
    }

    @Override
    public void loadfile(@NotNull String path) {
        File file = new File(path);
        if (!file.exists()) {
            RefineX.logger.log(LogType.ERROR, "File not found: " + file.getAbsolutePath(), LogSource.here());
            return;
        }

        JSONObject jsonObject = new JSONObject(file);
        Map<String, Object> map = jsonObject.toMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                for (Map.Entry<String, Object> subEntry : ((Map<String, Object>) value).entrySet()) {
                    String subKey = subEntry.getKey();
                    this.set(subKey, subEntry.getValue(), true);
                }
            }
            this.set(key, value, true);
        }
    }

    @Override
    public long functionReference(@NotNull Function function) {
        return 0;
    }

    @Override
    public Function getFunctionReference(long refId) {
        return null;
    }

    @Override
    public Object envTypeFunctionalObject(Object obj, boolean isStatic) {
        return obj;
    }

    @Override
    public void addEventHandler(@NotNull ContextEventHandler handler) { }

    @Override
    public boolean dispatchEvent(@NotNull ContextEvent event) {
        return false;
    }

    @Override
    public void tick(double frameTime) { }

    @Override
    public @NotNull LogSource currentSource() {
        return LogSource.here();
    }

    @Override
    public @NotNull Simulator getSimulator() {
        return this.simulator;
    }

    @Override
    public @NotNull List<Namespace> getNamespaces() {
        return List.of();
    }

    @Override
    public @NotNull String getResourcePath() {
        return FileUtils.currentDirectory().getAbsolutePath() + "/./resources/";
    }

    @Override
    public @NotNull Resource getResource() {
        return this.resource;
    }

    @Override
    public Language getLanguage() {
        return LanguageManager.getLanguage("JSON");
    }
}
