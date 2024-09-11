package todoList.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class Util {
    private static JSONArray taskListJson;
    
    public static void setTaskListJson(JSONArray taskListJson) {
        Util.taskListJson = taskListJson;
    }

    public static JSONArray getTaskListJson() {
        return taskListJson;
    }

    public static void putTaskListJson(JSONObject taskJson) {
        taskListJson.put(taskJson);
    }

    private String appData = System.getenv("APPDATA");
    private Path taskFilePath = Paths.get(appData, "TodoList", "tasks.json");
    private File pathConfigFile;
    private File taskFile;

    public Util() {
        this.pathConfigFile = new File("pathConfig.txt");

        FileReader fileReader = new FileReader(pathConfigFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        if(!bufferedReader.readLine().isEmpty()) {
            this.taskFile = new File(bufferedReader.readLine());
        }

    }

    public Boolean verifyPathConfigFile() {
        
        try {
            if (!pathConfigFile.exists()) {
                FileWriter fileWriter = new FileWriter(pathConfigFile);

                pathConfigFile.createNewFile();

                fileWriter.write(taskFile.getAbsolutePath());
                fileWriter.close();

                return true;
            }

            FileReader fileReader = new FileReader(taskFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // if the file is empty, just return
            if (bufferedReader.readLine() == null) {
                FileWriter fileWriter = new FileWriter(pathConfigFile);

                fileWriter.write(taskFile.getAbsolutePath());
                fileWriter.close();
                
                bufferedReader.close();
                fileReader.close();

                return true;
            }

            bufferedReader.close();
            fileReader.close();

            return false;
        } catch (IOException e) {
            return true;
        }

    }

    public Boolean verifyTaskFile() {
        try {
            if (!taskFile.exists()) {
                createTaskJsonFile();
                return true;
            }

            FileReader fileReader = new FileReader(taskFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // if the file is empty, just return
            if (bufferedReader.readLine() == null) {
                bufferedReader.close();
                fileReader.close();
                return true;
            }

            bufferedReader.close();
            fileReader.close();

            return false;
        } catch (IOException e) {
            return true;
        }

    }

    public void createTaskJsonFile() throws IOException {
        Files.createDirectories(taskFilePath.getParent());
        taskFile.createNewFile();
        return;
    }

    public void updateTaskJsonFile() throws IOException {
        FileWriter fileWriter = new FileWriter(taskFile, StandardCharsets.UTF_8);
        fileWriter.write(taskListJson.toString(4));
        fileWriter.close();
    }

}
