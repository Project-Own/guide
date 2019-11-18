package com.example.guide.Model;

public class MapsButton {
    private String name;
    private String image;


    public MapsButton(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public MapsButton(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
