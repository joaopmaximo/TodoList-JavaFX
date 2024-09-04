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

public class Util {
    static public JSONArray taskListJson;
    public static String appData = System.getenv("APPDATA");
    public static Path path = Paths.get(Util.appData, "TodoList", "tasks.json");
    public static File file = new File(Util.appData + "/TodoList/tasks.json");

    public static Boolean isFileEmptyOrNoExists(File file) {
        try {
            if (!file.exists()) {
                createTaskJsonFile();
                return true;
            }

            FileReader fileReader = new FileReader(file);
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

    public static void createTaskJsonFile() throws IOException {
        Files.createDirectories(path.getParent());
        file.createNewFile();
        return;
    }

    public static void updateTaskJsonFile() throws IOException {
        FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
        fileWriter.write(taskListJson.toString(4));
        fileWriter.close();
    }

}
