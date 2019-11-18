package com.example.guide.Model;

public class Food {
    private String name;
    private String description;
    private String image;

    public Food(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }


    public Food(String name, String image) {
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
