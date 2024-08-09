package com.example.exercise;

public class TestItem {
    private String title;
    private String description;
    private String extraInfo;

    public TestItem() {
    }

    public TestItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public TestItem(String title, String description, String extraInfo) {
        this.title = title;
        this.description = description;
        this.extraInfo = extraInfo;
    }

    public String getTitle() {
        return title;
    }

    public  String  getDescription() {
        return description;
    }

    public String getExtraInfo() {
        return extraInfo;
    }
}
