package todoList.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import todoList.model.Task;
import todoList.util.Util;

public class MainController {

    @FXML
    private Pane mainPane;

    @FXML
    private VBox taskList;

    @FXML
    private TextField newTaskField;

    // get all the persisted tasks in the json file to the app
    public void getTasks() {
        try {

            if (Util.isFileEmptyOrNoExists(Util.file)) {
                Util.taskListJson = new JSONArray();
                return;
            }

            // get the content from the json file and put in a jsonArray
            String fileContent = new String(Files.readAllBytes(Util.path), StandardCharsets.UTF_8);
            Util.taskListJson = new JSONArray(fileContent);

            // for each jsonObject in the jsonArray, create a task in the application using
            // the FXML file
            for (int i = 0; i < Util.taskListJson.length(); i++) {
                JSONObject taskJson = new JSONObject(Util.taskListJson.getJSONObject(i).toMap());
                Task task = new Task(taskJson.getString("content"), taskJson.getBoolean("checked"));
                addItemToTaskList(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;

            // if the json file is incorrect, creates a new one
        } catch (JSONException e) {
            try {
                Util.taskListJson = new JSONArray();
                Util.updateTaskJsonFile();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    // add a new task into the application and persists into a json file
    public void addTask() {

        // prevent empty tasks, checking if the textfield is empty
        if (newTaskField.getText().isBlank()) {
            return;
        }

        // create a new task, uses a FXML file as template in the application. the task
        // will be saved into a json file
        try {
            Task task = new Task(newTaskField.getText());
            addItemToTaskList(task);
            saveTask(task);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // cleans the textField for the next input
        newTaskField.clear();
    }

    // saves the task in a json file, it uses the library org.json
    public void saveTask(Task task) throws IOException {
        JSONObject taskJson = new JSONObject();
        taskJson.put("content", task.getContent());
        taskJson.put("checked", task.getChecked());

        Util.taskListJson.put(taskJson);
        Util.updateTaskJsonFile();
    }

    public void addItemToTaskList(Task task) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/task.fxml"));
        HBox taskItem = loader.load();
        TaskController taskController = loader.getController();
        taskController.setMainController(this);
        taskController.setData(task);
        taskList.getChildren().add(taskItem);
    }

    public void deleteItemFromTaskList(int index) {
        taskList.getChildren().remove(index);
    }

}
