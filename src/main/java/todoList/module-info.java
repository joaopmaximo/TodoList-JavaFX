module todoList {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires org.json;

    exports todoList;

    opens todoList to javafx.fxml;

    exports todoList.controller;

    opens todoList.controller to javafx.fxml;
}
