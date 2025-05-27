package org.rs.refinex.lua;

import org.rs.refinex.RefineX;
import org.rs.refinex.context.Namespace;
import org.rs.refinex.context.Native;
import org.rs.refinex.log.LogColor;
import org.rs.refinex.log.LogSource;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;

public class LuaNamespace extends Namespace {
    @Native
    public static void PRINT(Environment environment, String message, String file, int line) {
        RefineX.logger.log(LogType.DEBUG, LogColor.WHITE + "[" + LogColor.RESET +
                        environment.getSimulator().getName() + LogColor.WHITE + "] " + LogColor.RESET + message.trim(),
                new LogSource(file.replace(environment.getResourcePath(), ""), line)
        );
    }
}
