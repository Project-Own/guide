package com.example.guide.Model;

public class Food {
    private String name;
    private String description;
    private String image;



    private double lat;
    private double lang;

    public Food(String name, String description, String image,double lat, double lang) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.lat = lat;
        this.lang = lang;
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

    public double getLat() { return lat; }

    public void setLat(double lat) { this.lat = lat; }

    public double getLang() { return lang; }

    public void setLang(double lang) { this.lang = lang; }
}
