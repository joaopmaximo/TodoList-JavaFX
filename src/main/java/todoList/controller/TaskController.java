package todoList.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import todoList.model.Task;
import todoList.util.Util;

public class TaskController {

    @FXML
    private Label taskContent;

    @FXML
    private CheckBox checkedBox;

    // fill the FXML task template with the task data
    public void setData(Task task) {
        taskContent.setText(task.getContent());

        if (task.getChecked()) {
            checkedBox.setSelected(true);
        }
    }

    // saves the task in a json file, it uses the library org.json
    public static void saveTask(Task task) throws IOException {
        JSONObject taskJson = new JSONObject();
        taskJson.put("content", task.getContent());
        taskJson.put("checked", task.getChecked());

        MainController.taskListJson.put(taskJson);
        updateTaskJsonFile();
    }

    public static void updateTaskJsonFile() throws JSONException, IOException {
        FileWriter fileWriter = new FileWriter(Util.file, StandardCharsets.UTF_8);
        fileWriter.write(MainController.taskListJson.toString(4));
        fileWriter.close();
    }

    public void toggleChecked() throws IOException {

        JSONObject taskJson = new JSONObject();
        taskJson.put("content", taskContent.getText());
        taskJson.put("checked", checkedBox.isSelected());

        int i = 0;

        while (taskContent.getText() != MainController.taskListJson.getJSONObject(i).get("content")) {
            i++;
        }

        // updating the checked attribute from the found task
        MainController.taskListJson.getJSONObject(i).put("checked", checkedBox.isSelected());

        // saving the list with the updated task to the json file
        updateTaskJsonFile();
    }

}
