package com.example.gamezone.data.models;

public class Game {

    private int id;
    private String title;
    private int image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Game (int id, String title, int image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

}


