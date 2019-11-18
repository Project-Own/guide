package com.example.guide.Model;

public class Info {
    public String info;
    public String ticketInfo;
    public String price;
    public String about;

    public String getInfo() {
        return info;
    }

    public Info() {
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(String ticketInfo) {
        this.ticketInfo = ticketInfo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Info(String info, String ticketInfo, String price, String about) {
        this.info = info;
        this.ticketInfo = ticketInfo;
        this.price = price;
        this.about = about;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
