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

import todoList.util.LogConfig;

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
            defaultPath = Paths.get(userHome, ".config", "todoList");
        }
    }

    public FileController() {
        initConfigFile();
        initTasksFile();
        initMainColorFile();
    }

    private void initConfigFile() {
        try {
            this.configFile = new File(defaultPath.resolve("config.json").toString());
            if (!this.configFile.isFile()) {
                // creates the directory and the file
                this.configFile.getParentFile().mkdirs();
                this.configFile.createNewFile();
                this.configJson = new JSONObject();

                this.configJson.put("tasksFilePath", defaultPath.toString());
                this.configJson.put("mainColor", defaultMainColor);

                Files.writeString(this.configFile.toPath(), this.configJson.toString(4));

                return;
            }

            String fileContent = new String(Files.readAllBytes(this.configFile.toPath()));

            this.configJson = new JSONObject(fileContent);
        } catch (IOException e) {
            LogConfig.logAndShowError("Erro nos arquivos", e);
        }
    }

    private void initTasksFile() {
        try {
            this.tasksFile = new File(
                    Paths.get(this.configJson.getString("tasksFilePath")).resolve("tasks.json").toString());
            if (!this.tasksFile.isFile()) {
                // creates the directory and the file
                this.tasksFile.getParentFile().mkdirs();
                this.tasksFile.createNewFile();
                updateTasksFile(new JSONArray());
            }
        } catch (IOException e) {
            LogConfig.logAndShowError("Erro nos arquivos", e);
        }
    }

    private void initMainColorFile() {
        try {
            this.mainColorFile = new File(defaultPath.resolve("mainColor.css").toString());
            if (!this.mainColorFile.isFile()) {
                String cssDefaultContent = "* {\n" + "    -fx-main-color: " + defaultMainColor + ";\n}";

                // creates the directory and the file
                this.mainColorFile.getParentFile().mkdirs();
                this.mainColorFile.createNewFile();

                Files.writeString(this.mainColorFile.toPath(), cssDefaultContent);
            }
        } catch (IOException e) {
            LogConfig.logAndShowError("Erro nos arquivos", e);
        }
    }

    public void updateTasksFile(JSONArray tasksListJson) {
        try {
            FileWriter fileWriter = new FileWriter(this.tasksFile, StandardCharsets.UTF_8);
            fileWriter.write(tasksListJson.toString(4));
            fileWriter.close();
        } catch (IOException e) {
            LogConfig.logAndShowError("Erro nos arquivos", e);
        }
    }

    public void updateConfigFile(JSONObject configJson) {
        try {
            Files.writeString(configFile.toPath(), configJson.toString(4));
        } catch (IOException e) {
            LogConfig.logAndShowError("Erro nos arquivos", e);
        }
    }

    public File getTasksFile() {
        return this.tasksFile;
    }

    public String getTasksFileContent() throws IOException {
        String fileContent = new String(Files.readAllBytes(this.tasksFile.toPath()), StandardCharsets.UTF_8);
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
            Files.writeString(this.mainColorFile.toPath(), cssNewContent);
        } catch (IOException e) {
            LogConfig.logAndShowError("Erro nos arquivos", e);
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
        return this.mainColorFile.toPath();
    }

    public String getMainColor() {
        return this.configJson.getString("mainColor");
    }
}
