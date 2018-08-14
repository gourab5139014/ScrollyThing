package edu.buffalo.cse.odin.scrollything.models;

public class RecyclerRow {
    private String text;
    private int id;

    public RecyclerRow(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public RecyclerRow(String text){
        this(text,0);
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
