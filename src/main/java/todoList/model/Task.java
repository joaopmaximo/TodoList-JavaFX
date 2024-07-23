package todoList.model;

public class Task {
    private String content;
    private Boolean checked;

    public Task(String content, Boolean checked) {
        this.content = content;
        this.checked = checked;
    }

    public Task(String content) {
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

}
