package todoList.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

public class LogConfig {
    private static final String os = System.getProperty("os.name").toLowerCase();
    private static final Path defaultPath;
    private static Logger logger;

    // adjust the main directory according to the OS
    static {
        if (os.contains("win")) {
            final String appData = System.getenv("APPDATA");
            defaultPath = Paths.get(appData, "todoList");
        } else {
            final String userHome = System.getProperty("user.home");
            defaultPath = Paths.get(userHome, ".config", "todoList");
        }
    }

    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger("todoListLogger");
            logger.setUseParentHandlers(false);
            try {
                String logFilePath = defaultPath.resolve("log.txt").toString();
                FileHandler fileHandler = new FileHandler(logFilePath, true);
                fileHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(fileHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }

}
