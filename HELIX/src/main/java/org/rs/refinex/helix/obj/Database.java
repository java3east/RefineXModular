package org.rs.refinex.helix.obj;

import java.util.HashMap;
import java.util.List;

import org.rs.refinex.RefineX;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.util.Timer;
import org.rs.refinex.value.Any;
import org.rs.refinex.value.ExportFunction;
import org.rs.refinex.value.Referencable;
import org.rs.refinex.value.Varargs;

import net.refinedsolution.database.SimpleDatabase;

public class Database extends Referencable {
    private static final HashMap<String, SimpleDatabase> databases = new HashMap<>();
    private static final HashMap<Environment, String> environmentDatabaseMap = new HashMap<>();

    @ExportFunction
    public static void Initialize(final Environment environment, final String path) {
        environmentDatabaseMap.put(environment, path);
        if (databases.containsKey(path)) return;
        SimpleDatabase database = new SimpleDatabase();
        databases.put(path, database);
    }

    @ExportFunction
    public static boolean Execute(final Environment environment, final String sql, final Varargs parameters) {
        Timer timer = new Timer();
        List<Any> args = environment.getLanguage().getValueMapper().baseTypes(parameters);
        Any[] argsArr = (Any[]) args.get(0).o();
        Object[] argsObj = new Object[argsArr.length];
        for (int i = 0; i < argsArr.length; i++) {
            argsObj[i] = argsArr[i].o();
        }
        String path = environmentDatabaseMap.get(environment);
        if (path == null) {
            RefineX.logger.log(LogType.ERROR, "No database initialized for environment", LogSource.here());
            return false;
        }
        SimpleDatabase database = databases.get(path);
        if (database == null) {
            RefineX.logger.log(LogType.ERROR, "Database not found for path: " + path, LogSource.here());
            return false;
        }
        boolean success = database.execute(sql, argsObj);
        long ms = timer.millis();
        RefineX.logger.log(LogType.INFO, "Executed SQL '" + sql + "' (took " + ms + "ms)", LogSource.here());
        return success;
    }

    @ExportFunction
    public static HelixDatabaseResult.Row[] Select(final Environment environment, final String sql, final Varargs parameters) {
        Timer timer = new Timer();
        List<Any> args = environment.getLanguage().getValueMapper().baseTypes(parameters);
        Any[] argsArr = (Any[]) args.get(0).o();
        Object[] argsObj = new Object[argsArr.length];
        for (int i = 0; i < argsArr.length; i++) {
            argsObj[i] = argsArr[i].o();
        }
        String path = environmentDatabaseMap.get(environment);
        if (path == null) {
            RefineX.logger.log(LogType.ERROR, "No database initialized for environment", LogSource.here());
            return null;
        }
        SimpleDatabase database = databases.get(path);
        if (database == null) {
            RefineX.logger.log(LogType.ERROR, "Database not found for path: " + path, LogSource.here());
            return null;
        }

        List<HashMap<String, Object>> results = database.query(sql, argsObj);
        HelixDatabaseResult.Row[] rows = new HelixDatabaseResult.Row[results.size()];
        int i = 0;
        for (HashMap<String, Object> row : results) {
            rows[i++] = new HelixDatabaseResult.Row(row);
        }
        long ms = timer.millis();
        RefineX.logger.log(LogType.INFO, "Executed SQL '" + sql + "' (took " + ms + "ms)", LogSource.here());
        return rows;
    }
}
