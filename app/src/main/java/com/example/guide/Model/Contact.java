package com.example.guide.Model;

public class Contact {
 private String name;
 private String number;
 private String photo;
 private String location;




    public Contact(String name, String number, String photo, String location) {
        this.name = name;
        this.number = number;
        this.photo = photo;
        this.location = location;
    }




    public Contact(String name, String number, String photo) {
        this.name = name;
        this.number = number;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }




}
