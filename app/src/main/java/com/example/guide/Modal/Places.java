package com.example.guide.Modal;

public class Places {
    private String name;
    private String description;
    private String image;

    public Places(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }


    public Places(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
