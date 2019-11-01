package com.example.guide.Modal;

public class Recomendation {
 private String name;
    private String rating;
    private String phone;
    private String vicinity;
    private String price;
    private String description;
    private String photo;

    public Recomendation(String name, String rating, String phone, String vicinity, String price, String description, String photo) {
        this.name = name;
        this.rating = rating;
        this.phone = phone;
        this.vicinity = vicinity;
        this.price = price;
        this.description = description;
        this.photo = photo;
    }

    public Recomendation(String name, String rating, String phone, String vicinity, String price, String description) {
        this.name = name;
        this.rating = rating;
        this.phone = phone;
        this.vicinity = vicinity;
        this.price = price;
        this.description = description;
    }

    public Recomendation(String name, String rating, String phone, String vicinity, String price) {
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


}
