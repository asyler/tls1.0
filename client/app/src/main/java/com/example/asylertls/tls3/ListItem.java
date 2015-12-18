package com.example.asylertls.tls3;

/**
 * Created by asyler on 15/11/15.
 */
public class ListItem {

    private String title;
    private String description;
    private String value;
    private String type;
    private boolean checked;

    public ListItem(String title, String description, String value, String type) {
        super();
        this.title = title;
        this.description = description;
        this.value = value;
        this.type = type;
        this.checked = true;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    // getters and setters...
}
