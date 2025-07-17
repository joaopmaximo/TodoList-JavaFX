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
    private String appData;
    private Path defaultTasksFilePath;
    private File configFile;
    private JSONObject configJson;
    private File tasksFile;

    public FileController() {
        appData = System.getenv("APPDATA");
        defaultTasksFilePath = Paths.get(appData, "todoList");
        initConfigFile();
        initTasksFile();
    }

    private void initConfigFile() {
        try {
            configFile = new File("config.json");
            if (!configFile.isFile()) {
                configFile.createNewFile();
                configJson = new JSONObject();

                configJson.put("tasksFilePath", defaultTasksFilePath.toString());

                Files.writeString(configFile.toPath(), configJson.toString(4));

                return;
            }

            String fileContent = new String(Files.readAllBytes(configFile.toPath()), StandardCharsets.UTF_8);

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
            FileWriter fileWriter = new FileWriter(configFile, StandardCharsets.UTF_8);
            fileWriter.write(configJson.toString(4));
            fileWriter.close();
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
        JSONObject newConfigJson = new JSONObject();
        newConfigJson.put("tasksFilePath", newPath);
        updateConfigFile(newConfigJson);
        this.configJson = newConfigJson;
        initTasksFile();
    }

    public Path getDefaultTasksFilePath() {
        return defaultTasksFilePath;
    }
}
