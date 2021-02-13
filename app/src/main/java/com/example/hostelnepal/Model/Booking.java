package com.example.hostelnepal.Model;

public class Booking {
    private String documentId,hostelName,roomType,price;

    public Booking() {
        //no argument constructor
    }

    public Booking(String documentId, String hostelName, String roomType, String price) {
        this.documentId = documentId;
        this.hostelName = hostelName;
        this.roomType = roomType;
        this.price = price;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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
}
