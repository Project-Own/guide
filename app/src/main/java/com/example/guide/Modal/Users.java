package com.example.guide.Modal;

public class Users {
    private String name;
    private String description;
    private String link;
    private int image;

    public Users(String name, String description, String link) {
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public Users(String name, String description, String link, int image) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.image = image;
    }

    public Users(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
