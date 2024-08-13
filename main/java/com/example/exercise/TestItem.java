package com.example.exercise;

import java.util.HashMap;
import java.util.Map;

public class TestItem {
    private String title;
    private String description;
    private String extraInfo;
    private Map<Integer, Boolean> checkBoxStates;

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
        this.checkBoxStates = new HashMap<>();
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

    public Map<Integer, Boolean> getCheckBoxStates() {
        return checkBoxStates;
    }

    public void setCheckBoxStates(int checkBoxID, boolean state) {
        checkBoxStates.put(checkBoxID, state);
    }
}
