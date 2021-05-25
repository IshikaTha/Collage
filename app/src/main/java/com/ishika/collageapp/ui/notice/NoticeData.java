package com.ishika.collageapp.ui.notice;

public class NoticeData {
    String title, date, image, time, key;

    public NoticeData() {
    }

    public NoticeData(String title, String date, String image, String time, String key) {
        this.title = title;
        this.date = date;
        this.image = image;
        this.time = time;
        this.key = key;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
