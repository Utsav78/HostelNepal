package com.example.hostelnepal.Model;

public class Booking {
    private String hostelId,hostelName,roomType,price;
    private String date;
    private long timestamp;
    private String status;
    private String ownerId;

    public Booking() {
        //no argument constructor
    }

    public Booking(String hostelId, String hostelName, String roomType, String price,String date,
                   long timestamp, String status,String ownerId) {
        this.hostelId = hostelId;
        this.hostelName = hostelName;
        this.roomType = roomType;
        this.price = price;
        this.date=date;
        this.timestamp = timestamp;
        this.status = status;
        this.ownerId = ownerId;
    }

    public String getDocumentId() {
        return hostelId;
    }

    public void setDocumentId(String hostelId) {
        this.hostelId = hostelId;
    }

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}

