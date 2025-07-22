package todoList;

import java.util.logging.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import todoList.controller.FileController;
import todoList.controller.MainController;
import todoList.util.LogConfig;

public class Main extends Application {
    private static final Logger logger = LogConfig.getLogger();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
            Parent root = loader.load(); // root node, required by scene
            Scene scene = new Scene(root); // scene, required by stage

            FileController fileController = new FileController();

            String stylesCss = fileController.getStyleFilePath();
            String darkModeCss = fileController.getDarkModeStyleFilePath();
            String mainColorCss = fileController.getMainColorFilePath().toUri().toString();

            scene.getStylesheets().add(stylesCss);
            scene.getStylesheets().add(darkModeCss);
            scene.getStylesheets().add(mainColorCss);
            
            Image icon = new Image(fileController.getIconFilePath());
            MainController mainController = loader.getController();
            mainController.getTasks();

            stage.setTitle("To Do List");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado", e);
        }
    }

}