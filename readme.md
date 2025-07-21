# To Do List with JavaFX

![TodoList App](/src/main/resources/img/dark-app-print.png?raw=true "TodoList")

## How to distribute the application using Jpackage

Generate a runtime image, ensure that all modules are included in the module-info.java. I used javafx maven plugin in this

```console
mvn javafx:jlink
```

Finally use Jpackage to create the installer

```console
jpackage --name todoList  --app-version 3.0 --module todoList/todoList.Main --runtime-image target/image --icon src/main/resources/img/lista.ico --win-menu --win-shortcut
```
## Features

### Dark/light mode

![TodoList App print](/src/main/resources/img/light-app-print.png?raw=true "TodoList")

### Change tasks directory & config file

![App Files print](/src/main/resources/img/config-files-print.png?raw=true "App files")

### Customize main color

![Customizing colors print](/src/main/resources/img/customize-color-print.png?raw=true "Customize colors")