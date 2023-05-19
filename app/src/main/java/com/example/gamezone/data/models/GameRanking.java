package com.example.gamezone.data.models;

public class GameRanking {

    String title;
    String goldUsername;
    String goldPoints;
    String silverUsername;
    String silverPoints;
    String bronzeUsername;
    String bronzePoints;

    public GameRanking(String title, String goldUsername, String goldPoints, String silverUsername, String silverPoints, String bronzeUsername, String bronzePoints) {
        this.title = title;
        this.goldUsername = goldUsername;
        this.goldPoints = goldPoints;
        this.silverUsername = silverUsername;
        this.silverPoints = silverPoints;
        this.bronzeUsername = bronzeUsername;
        this.bronzePoints = bronzePoints;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGoldUsername() {
        return goldUsername;
    }

    public void setGoldUsername(String goldUsername) {
        this.goldUsername = goldUsername;
    }

    public String getGoldPoints() {
        return goldPoints;
    }

    public void setGoldPoints(String goldPoints) {
        this.goldPoints = goldPoints;
    }

    public String getSilverUsername() {
        return silverUsername;
    }

    public void setSilverUsername(String silverUsername) {
        this.silverUsername = silverUsername;
    }

    public String getSilverPoints() {
        return silverPoints;
    }

    public void setSilverPoints(String silverPoints) {
        this.silverPoints = silverPoints;
    }

    public String getBronzeUsername() {
        return bronzeUsername;
    }

    public void setBronzeUsername(String bronzeUsername) {
        this.bronzeUsername = bronzeUsername;
    }

    public String getBronzePoints() {
        return bronzePoints;
    }

    public void setBronzePoints(String bronzePoints) {
        this.bronzePoints = bronzePoints;
    }
}
