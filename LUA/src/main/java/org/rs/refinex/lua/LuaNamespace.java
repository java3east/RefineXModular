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
        message = message
                .replace("^0", LogColor.RESET.ansi())
                .replace("^1", LogColor.BLACK.ansi())
                .replace("^2", LogColor.RED.ansi())
                .replace("^3", LogColor.GREEN.ansi())
                .replace("^4", LogColor.YELLOW.ansi())
                .replace("^5", LogColor.BLUE.ansi())
                .replace("^6", LogColor.PURPLE.ansi())
                .replace("^7", LogColor.CYAN.ansi())
                .replace("^8", LogColor.WHITE.ansi())
                .replace("^9", LogColor.BOLD.ansi());

        RefineX.logger.log(LogType.DEBUG, LogColor.WHITE + "[" + LogColor.RESET +
                        environment.getSimulator().getName() + LogColor.WHITE + "] " + LogColor.RESET + message.trim() +
                        LogColor.RESET,
                new LogSource(file.replace(environment.getResourcePath(), ""), line)
        );
    }
}
