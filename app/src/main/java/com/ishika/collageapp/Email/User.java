package com.ishika.collageapp.Email;

public class User {
    private String name;
    private String email;
    private String photo;
    private String year;
    private String stream;


    public User() {
    }

    public User(String name, String email, String photo, String year, String stream) {
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.year = year;
        this.stream = stream;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
