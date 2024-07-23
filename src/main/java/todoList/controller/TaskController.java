package todoList.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import todoList.model.Task;

public class TaskController {

    @FXML
    private Label text;

    public void setData(Task task) {
        text.setText(task.getContent());
    }

    public void saveTask(Task task) throws IOException {
        Path path = Paths.get("tasks.json");
        File file = new File("tasks.json");

        JSONObject taskJson = new JSONObject();
        taskJson.put("content", task.getContent());
        taskJson.put("checked", task.getChecked());

        // if the file not exists, create the file with a list including the first task
        if (!file.exists()) {
            FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
            JSONArray tasksListJson = new JSONArray();
            tasksListJson.put(taskJson);
            fileWriter.write(tasksListJson.toString(4));
            fileWriter.close();
            return;
        }

        // if the file not exists, overwite the file with a list including the first task
        if (isFileEmpty(file)) {
            FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
            JSONArray tasksListJson = new JSONArray();
            tasksListJson.put(taskJson);
            fileWriter.write(tasksListJson.toString(4));
            fileWriter.close();
            return;
        }

        // if the file already has a task, get the file content and add the new task to it

        String content = new String(Files.readAllBytes(path));
        
        JSONArray tasksListJson = new JSONArray(content);
        tasksListJson.put(taskJson);
        
        FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
        fileWriter.write(tasksListJson.toString(4));
        fileWriter.close();
    }

    public Boolean isFileEmpty(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            if (bufferedReader.readLine() == null) {
                fileReader.close();
                bufferedReader.close();
                return true;
            }

            if (bufferedReader.readLine().isBlank()) {
                fileReader.close();
                bufferedReader.close();
                return true;
            }

            fileReader.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
