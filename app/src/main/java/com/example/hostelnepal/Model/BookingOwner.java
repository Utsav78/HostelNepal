package com.example.hostelnepal.Model;

public class BookingOwner {
    private String hostelId;
    private String hostelName;
    private String phoneNumber;
    private String email;
    private String roomType;
    private String bookingPrice;
    private String guestName;

    private String date;
    private long timestamp;
    private String guestId;


    public BookingOwner() {
        //no argument constructor
    }

    public BookingOwner(String hostelId, String hostelName,
                        String phoneNumber,
                        String email, String roomType,
                        String bookingPrice, String guestName,String date,long timestamp,String guestId) {

        this.hostelId = hostelId;
        this.hostelName = hostelName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roomType = roomType;
        this.bookingPrice = bookingPrice;
        this.guestName = guestName;
        this.date = date;
        this.timestamp = timestamp;
        this.guestId = guestId;

    }

    public String getHostelId() {
        return hostelId;
    }

    public void setHostelId(String documentId) {
        this.hostelId= documentId;
    }

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(String bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
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

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }
}
