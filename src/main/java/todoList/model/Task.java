package todoList.model;

public class Task {
    private int id;
    private String content;
    private Boolean checked;

    public Task(int id ,String content, Boolean checked) {
        this.id = id;
        this.content = content;
        this.checked = checked;
    }

    public Task(int id, String content) {
        this.id = id;
        this.content = content;
        this.checked = false;
    }

    public Task() {
    }
    
    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

}
