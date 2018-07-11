package com.example.charlie.chorestarapp;

public class Chore {
    private int ChoreImage;
    private String ChoreName;
    private String ChoreChildName;
    private String ChorePoints;

    public Chore(int choreImage, String choreName, String choreChildName, String chorePoints) {
        ChoreImage = choreImage;
        ChoreName = choreName;
        ChoreChildName = choreChildName;
        ChorePoints = chorePoints;
    }

    public int getChoreImage() {
        return ChoreImage;
    }

    public String getChoreName() {
        return ChoreName;
    }

    public String getChoreChildName() {
        return ChoreChildName;
    }

    public String getChorePoints() {
        return ChorePoints;
    }
}
