package com.example.guide.Modal;

import android.widget.TextView;

public class Users {
 private String name;
 private String roll;
 private  String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Users(String name, String roll, String email) {
        this.name = name;
        this.roll = roll;
        this.email = email;
    }
}
