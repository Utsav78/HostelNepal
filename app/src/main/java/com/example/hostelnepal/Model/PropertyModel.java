package com.example.hostelnepal.Model;


public class PropertyModel {
    private String propertyDescription;
    public String nameOfHostel,hostelType,city,locality;
    public Integer priceOfRoom1,priceOfRoom2,priceOfRoom3,priceOfRoom4;
    public String userID;
    public String uriOfRoom1,uriOfRoom2,uriOfRoom3,
            uriOfRoom4,uriOfWashroom,uriOfEnvironment,uriOfBuilding,uriOfDocument,uriOfKitchen;
    public Boolean checkBoxOfWifi,checkBoxOfElectricity,checkBoxOfWater,
            checkBoxOfLaundry,checkBoxOfParking,checkBoxOfCCTV,checkBoxOfSecurity,checkBoxOfPlayground;
    public Integer availableBeds1,availableBeds2,availableBeds3,availableBeds4;

    public PropertyModel(){}

    public PropertyModel(String nameOfHostel, String hostelType, String city, String locality,
                         Integer priceOfRoom1, Integer priceOfRoom2, Integer priceOfRoom3, Integer priceOfRoom4,
                         String uriOfRoom1, String uriOfRoom2, String uriOfRoom3, String uriOfRoom4,String uriOfDocument, String uriOfWashroom,
                         String uriOfBuilding,String uriOfKitchen,String uriOfEnvironment,
                         Boolean checkBoxOfWifi, Boolean checkBoxOfElectricity, Boolean checkBoxOfWater,
                         Boolean checkBoxOfLaundry, Boolean checkBoxOfParking, Boolean checkBoxOfCCTV,
                         Boolean checkBoxOfSecurity, Boolean checkBoxOfPlayground,String userID,Integer availableBeds1
                        ,Integer availableBeds2,Integer availableBeds3,Integer availableBeds4,
                         String propertyDescription) {
        this.nameOfHostel = nameOfHostel;
        this.hostelType = hostelType;
        this.city = city;
        this.locality = locality;
        this.priceOfRoom1 = priceOfRoom1;
        this.priceOfRoom2 = priceOfRoom2;
        this.priceOfRoom3 = priceOfRoom3;
        this.priceOfRoom4 = priceOfRoom4;
        this.uriOfRoom1 = uriOfRoom1;
        this.uriOfRoom2 = uriOfRoom2;
        this.uriOfRoom3 = uriOfRoom3;
        this.uriOfRoom4 = uriOfRoom4;
        this.uriOfWashroom = uriOfWashroom;
        this.uriOfEnvironment = uriOfEnvironment;
        this.uriOfBuilding = uriOfBuilding;
        this.uriOfDocument = uriOfDocument;
        this.uriOfKitchen = uriOfKitchen;
        this.checkBoxOfWifi = checkBoxOfWifi;
        this.checkBoxOfElectricity = checkBoxOfElectricity;
        this.checkBoxOfWater = checkBoxOfWater;
        this.checkBoxOfLaundry = checkBoxOfLaundry;
        this.checkBoxOfParking = checkBoxOfParking;
        this.checkBoxOfCCTV = checkBoxOfCCTV;
        this.checkBoxOfSecurity = checkBoxOfSecurity;
        this.checkBoxOfPlayground = checkBoxOfPlayground;
        this.userID = userID;
        this.availableBeds1=availableBeds1;
        this.availableBeds2=availableBeds2;
        this.availableBeds3 = availableBeds3;
        this.availableBeds4=availableBeds4;
        this.propertyDescription = propertyDescription;
    }

    public String getNameOfHostel() {
        return nameOfHostel;
    }

    public void setNameOfHostel(String nameOfHostel) {
        this.nameOfHostel = nameOfHostel;
    }

    public String getHostelType() {
        return hostelType;
    }

    public void setHostelType(String hostelType) {
        this.hostelType = hostelType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Integer getPriceOfRoom1() {
        return priceOfRoom1;
    }

    public void setPriceOfRoom1(Integer priceOfRoom1) {
        this.priceOfRoom1 = priceOfRoom1;
    }

    public Integer getPriceOfRoom2() {
        return priceOfRoom2;
    }

    public void setPriceOfRoom2(Integer priceOfRoom2) {
        this.priceOfRoom2 = priceOfRoom2;
    }

    public Integer getPriceOfRoom3() {
        return priceOfRoom3;
    }

    public void setPriceOfRoom3(Integer priceOfRoom3) {
        this.priceOfRoom3 = priceOfRoom3;
    }

    public Integer getPriceOfRoom4() {
        return priceOfRoom4;
    }

    public void setPriceOfRoom4(Integer priceOfRoom4) {
        this.priceOfRoom4 = priceOfRoom4;
    }

    public String getUriOfRoom1() {
        return uriOfRoom1;
    }

    public void setUriOfRoom1(String uriOfRoom1) {
        this.uriOfRoom1 = uriOfRoom1;
    }

    public String getUriOfRoom2() {
        return uriOfRoom2;
    }

    public void setUriOfRoom2(String uriOfRoom2) {
        this.uriOfRoom2 = uriOfRoom2;
    }

    public String getUriOfRoom3() {
        return uriOfRoom3;
    }

    public void setUriOfRoom3(String uriOfRoom3) {
        this.uriOfRoom3 = uriOfRoom3;
    }

    public String getUriOfRoom4() {
        return uriOfRoom4;
    }

    public void setUriOfRoom4(String uriOfRoom4) {
        this.uriOfRoom4 = uriOfRoom4;
    }

    public String getUriOfWashroom() {
        return uriOfWashroom;
    }

    public void setUriOfWashroom(String uriOfWashroom) {
        this.uriOfWashroom = uriOfWashroom;
    }

    public String getUriOfEnvironment() {
        return uriOfEnvironment;
    }

    public void setUriOfEnvironment(String uriOfEnvironment) {
        this.uriOfEnvironment = uriOfEnvironment;
    }

    public String getUriOfBuilding() {
        return uriOfBuilding;
    }

    public void setUriOfBuilding(String uriOfBuilding) {
        this.uriOfBuilding = uriOfBuilding;
    }

    public String getUriOfDocument() {
        return uriOfDocument;
    }

    public void setUriOfDocument(String uriOfDocument) {
        this.uriOfDocument = uriOfDocument;
    }

    public String getUriOfKitchen() {
        return uriOfKitchen;
    }

    public void setUriOfKitchen(String uriOfKitchen) {
        this.uriOfKitchen = uriOfKitchen;
    }

    public Boolean getCheckBoxOfWifi() {
        return checkBoxOfWifi;
    }

    public void setCheckBoxOfWifi(Boolean checkBoxOfWifi) {
        this.checkBoxOfWifi = checkBoxOfWifi;
    }

    public Boolean getCheckBoxOfElectricity() {
        return checkBoxOfElectricity;
    }

    public void setCheckBoxOfElectricity(Boolean checkBoxOfElectricity) {
        this.checkBoxOfElectricity = checkBoxOfElectricity;
    }

    public Boolean getCheckBoxOfWater() {
        return checkBoxOfWater;
    }

    public void setCheckBoxOfWater(Boolean checkBoxOfWater) {
        this.checkBoxOfWater = checkBoxOfWater;
    }

    public Boolean getCheckBoxOfLaundry() {
        return checkBoxOfLaundry;
    }

    public void setCheckBoxOfLaundry(Boolean checkBoxOfLaundry) {
        this.checkBoxOfLaundry = checkBoxOfLaundry;
    }

    public Boolean getCheckBoxOfParking() {
        return checkBoxOfParking;
    }

    public void setCheckBoxOfParking(Boolean checkBoxOfParking) {
        this.checkBoxOfParking = checkBoxOfParking;
    }

    public Boolean getCheckBoxOfCCTV() {
        return checkBoxOfCCTV;
    }

    public void setCheckBoxOfCCTV(Boolean checkBoxOfCCTV) {
        this.checkBoxOfCCTV = checkBoxOfCCTV;
    }

    public Boolean getCheckBoxOfSecurity() {
        return checkBoxOfSecurity;
    }

    public void setCheckBoxOfSecurity(Boolean checkBoxOfSecurity) {
        this.checkBoxOfSecurity = checkBoxOfSecurity;
    }

    public Boolean getCheckBoxOfPlayground() {
        return checkBoxOfPlayground;
    }

    public void setCheckBoxOfPlayground(Boolean checkBoxOfPlayground) {
        this.checkBoxOfPlayground = checkBoxOfPlayground;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getAvailableBeds1() {
        return availableBeds1;
    }

    public void setAvailableBeds1(Integer availableBeds1) {
        this.availableBeds1 = availableBeds1;
    }

    public Integer getAvailableBeds2() {
        return availableBeds2;
    }

    public void setAvailableBeds2(Integer availableBeds2) {
        this.availableBeds2 = availableBeds2;
    }

    public Integer getAvailableBeds3() {
        return availableBeds3;
    }

    public void setAvailableBeds3(Integer availableBeds3) {
        this.availableBeds3 = availableBeds3;
    }

    public Integer getAvailableBeds4() {
        return availableBeds4;
    }

    public void setAvailableBeds4(Integer availableBeds4) {
        this.availableBeds4 = availableBeds4;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }
}
