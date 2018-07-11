package com.example.charlie.chorestarapp;

public class Profile {
    private int ProfileImage;
    private String ProfileName;
    private String ProfilePoints;
    private String ProfileAge;

    public Profile(int profileImage, String profileName, String profilePoints, String profileAge) {
        ProfileImage = profileImage;
        ProfileName = profileName;
        ProfilePoints = profilePoints;
        ProfileAge = profileAge;
    }

    public int getProfileImage() {
        return ProfileImage;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public String getProfilePoints() {
        return ProfilePoints;
    }

    public String getProfileAge() {
        return ProfileAge;
    }
}
