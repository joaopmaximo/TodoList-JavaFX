package todoList;

import org.json.JSONArray;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import todoList.controller.MainController;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
            Parent root = loader.load(); // root node, required by scene
            Scene scene = new Scene(root); // scene, required by stage

            String stylesCss = getClass().getResource("/css/styles.css").toExternalForm();
            String darkModeCss = getClass().getResource("/css/dark-mode.css").toExternalForm();

            scene.getStylesheets().add(stylesCss);
            scene.getStylesheets().add(darkModeCss);
            scene.setFill(Color.TRANSPARENT);
            
            Image icon = new Image("/img/lista.png");
            MainController mainController = loader.getController();
            mainController.getTasksPath();
            mainController.getTasks();

            stage.setTitle("Jeyp ToDo List");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}