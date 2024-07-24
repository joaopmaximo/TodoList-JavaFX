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

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import todoList.model.Task;
import todoList.util.Util;

public class TaskController {

    @FXML
    private Label text;

    // fill the FXML task template with the task data
    public void setData(Task task) {
        text.setText(task.getContent());
    }

    // saves the task in a json file, it uses the library org.json
    public void saveTask(Task task) throws IOException {
        Path path = Paths.get("tasks.json");
        File file = new File("tasks.json");

        JSONObject taskJson = new JSONObject();
        taskJson.put("content", task.getContent());
        taskJson.put("checked", task.getChecked());

        // if the file is empty or no exists, create the file with a list including the first task
        if (Util.isFileEmptyOrNoExists(file)) {
            FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
            JSONArray tasksListJson = new JSONArray();
            tasksListJson.put(taskJson);
            fileWriter.write(tasksListJson.toString(4));
            fileWriter.close();
            return;
        }

        // if the file already has a task, get the file content and add the new task to it

        String fileContent = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        
        JSONArray tasksListJson = new JSONArray(fileContent);
        tasksListJson.put(taskJson);
        
        FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
        fileWriter.write(tasksListJson.toString(4));
        fileWriter.close();
    }

}
