package com.example.hostelnepal.Model;

public class CommentModel {
    private String comment;
    private String date;
    private long timestamp;
    private String guestName;

    public CommentModel(String comment,String date,long timestamp,String guestName) {
        this.comment = comment;
        this.date = date;
        this.timestamp = timestamp;
        this.guestName = guestName;

    }

    public CommentModel() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
}
