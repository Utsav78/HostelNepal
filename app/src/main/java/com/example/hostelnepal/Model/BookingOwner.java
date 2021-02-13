package com.example.hostelnepal.Model;

public class BookingOwner {
    private String documentId;
    private String hostelName;
    private String phoneNumber;
    private String email;
    private String roomType;
    private String bookingPrice;
    private String guestName;

    public BookingOwner() {
        //no argument constructor
    }

    public BookingOwner(String documentId, String hostelName,
                        String phoneNumber,
                        String email, String roomType,
                        String bookingPrice, String guestName) {

        this.documentId = documentId;
        this.hostelName = hostelName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roomType = roomType;
        this.bookingPrice = bookingPrice;
        this.guestName = guestName;

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
}
