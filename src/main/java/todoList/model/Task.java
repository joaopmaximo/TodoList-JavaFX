package todoList.model;

public class Task {
    private String text;

    public Task() {
    }

    public Task (String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
