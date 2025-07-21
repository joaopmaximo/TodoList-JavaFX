package todoList.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileController {
    private static final String os = System.getProperty("os.name").toLowerCase();
    private static final Path defaultPath;
    private static final String defaultMainColor = "#0455BF";
    private final String styleFilePath = getClass().getResource("/css/styles.css").toExternalForm();
    private final String darkModeStyleFilePath = getClass().getResource("/css/dark-mode.css").toExternalForm();
    private final String iconFilePath = getClass().getResource("/img/lista.png").toString();
    private File configFile;
    private JSONObject configJson;
    private File tasksFile;
    private File mainColorFile;

    // adjust the main directory according to the OS
    static {
        if (os.contains("win")) {
            final String appData = System.getenv("APPDATA");
            defaultPath = Paths.get(appData, "todoList");
        } else {
            final String userHome = System.getProperty("user.home");
            defaultPath = Paths.get(userHome, ".todoList");
        }
    }

    public FileController() {
        initConfigFile();
        initTasksFile();
        initMainColorFile();
    }

    private void initConfigFile() {
        try {
            configFile = new File(defaultPath.toString().concat("\\config.json"));
            if (!configFile.isFile()) {
                configFile.createNewFile();
                configJson = new JSONObject();

                configJson.put("tasksFilePath", defaultPath.toString());
                configJson.put("mainColor", defaultMainColor);

                Files.writeString(configFile.toPath(), configJson.toString(4));

                return;
            }

            String fileContent = new String(Files.readAllBytes(configFile.toPath()));

            configJson = new JSONObject(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTasksFile() {
        try {
            tasksFile = new File(configJson.getString("tasksFilePath").concat("\\tasks.json"));
            if (!tasksFile.isFile()) {
                // creates the directory and the file
                tasksFile.getParentFile().mkdirs();
                tasksFile.createNewFile();
                updateTasksFile(new JSONArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMainColorFile() {
        try {
            mainColorFile = new File(defaultPath.toString().concat("\\mainColor.css"));
            if (!mainColorFile.isFile()) {
                String cssDefaultContent = "* {\n" + "    -fx-main-color: " + defaultMainColor + ";\n}";

                // creates the directory and the file
                mainColorFile.getParentFile().mkdirs();
                mainColorFile.createNewFile();

                Files.writeString(mainColorFile.toPath(), cssDefaultContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTasksFile(JSONArray tasksListJson) {
        try {
            FileWriter fileWriter = new FileWriter(tasksFile, StandardCharsets.UTF_8);
            fileWriter.write(tasksListJson.toString(4));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateConfigFile(JSONObject configJson) {
        try {
            Files.writeString(configFile.toPath(), configJson.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getTasksFile() {
        return tasksFile;
    }

    public String getTasksFileContent() throws IOException {
        String fileContent = new String(Files.readAllBytes(tasksFile.toPath()), StandardCharsets.UTF_8);
        return fileContent;
    }

    public void setTasksFilePath(String newPath) {
        this.configJson.remove("tasksFilePath");
        this.configJson.put("tasksFilePath", newPath);
        updateConfigFile(this.configJson);
        initTasksFile();
    }

    public void setMainColor(String newColor) {
        this.configJson.remove("mainColor");
        this.configJson.put("mainColor", newColor);
        updateConfigFile(this.configJson);

        String cssNewContent = "* {\n" + "    -fx-main-color: " + newColor + ";\n}";

        try {
            Files.writeString(mainColorFile.toPath(), cssNewContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path getdefaultPath() {
        return defaultPath;
    }

    public String getStyleFilePath() {
        return styleFilePath;
    }

    public String getDarkModeStyleFilePath() {
        return darkModeStyleFilePath;
    }

    public String getIconFilePath() {
        return iconFilePath;
    }

    public Path getMainColorFilePath() {
        return mainColorFile.toPath();
    }

    public String getMainColor() {
        return configJson.getString("mainColor");
    }
}
