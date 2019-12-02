package com.example.guide.Model;

public class Recommendation {
 private String name;
    private String rating;
    private String phone;
    private String vicinity;
    private String price;
    private String description;
    private String photo;
    private double lat;
    private double lang;

    public Recommendation(String name, String rating, String phone, String vicinity, String price, String description, String photo,double lat, double lang) {
        this.name = name;
        this.rating = rating;
        this.phone = phone;
        this.vicinity = vicinity;
        this.price = price;
        this.description = description;
        this.photo = photo;
        this.lat = lat;
        this.lang = lang;
    }

    public Recommendation(String name, String rating, String phone, String vicinity, String price, String description, String photo) {
        this.name = name;
        this.rating = rating;
        this.phone = phone;
        this.vicinity = vicinity;
        this.price = price;
        this.description = description;
        this.photo = photo;
    }

    public Recommendation(String name, String rating, String phone, String vicinity, String price) {
        this.name = name;
        this.rating = rating;
        this.phone = phone;
        this.vicinity = vicinity;
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

}
