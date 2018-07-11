package com.example.charlie.chorestarapp;

public class Alerts {
    private int ChoreImage;
    private String ChoreName;
    private String ChildName;
    private String ChorePoints;

    public Alerts(int choreImage, String choreName, String childName, String chorePoints) {
        ChoreImage = choreImage;
        ChoreName = choreName;
        ChildName = childName;
        ChorePoints = chorePoints;
    }

    public int getChoreImage() {
        return ChoreImage;
    }

    public String getChoreName() {
        return ChoreName;
    }

    public String getChildName() {
        return ChildName;
    }

    public String getChorePoints() {
        return ChorePoints;
    }
}
