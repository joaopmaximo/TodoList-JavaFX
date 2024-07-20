package todoList;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class taskPane extends Pane {
    public taskPane(String content) {
        Label label = new Label(content);
        this.setPrefSize(400, 40);
        this.getStyleClass().add("task");
        this.getChildren().add(label);
    }
}
