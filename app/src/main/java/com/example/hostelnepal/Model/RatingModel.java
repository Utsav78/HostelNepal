package com.example.hostelnepal.Model;

public class RatingModel {
    float ratingCleanliness,ratingSecurity,ratingEnvironment,ratingFacilities,
    ratingFood,ratingStaff,ratingValueForMoney;
    String comment;

    public RatingModel() {
    }

    public RatingModel(float ratingCleanliness,
                       float ratingSecurity,
                       float ratingEnvironment,
                       float ratingFacilities,
                       float ratingFood,
                       float ratingStaff,
                       float ratingValueForMoney,
                       String comment) {

        this.ratingCleanliness = ratingCleanliness;
        this.ratingSecurity = ratingSecurity;
        this.ratingEnvironment = ratingEnvironment;
        this.ratingFacilities = ratingFacilities;
        this.ratingFood = ratingFood;
        this.ratingStaff = ratingStaff;
        this.ratingValueForMoney = ratingValueForMoney;
        this.comment = comment;
    }

    public RatingModel(float ratingCleanliness, float ratingSecurity,
                       float ratingEnvironment, float ratingFacilities,
                       float ratingFood, float ratingStaff,
                       float ratingValueForMoney) {

        this.ratingCleanliness = ratingCleanliness;
        this.ratingSecurity = ratingSecurity;
        this.ratingEnvironment = ratingEnvironment;
        this.ratingFacilities = ratingFacilities;
        this.ratingFood = ratingFood;
        this.ratingStaff = ratingStaff;
        this.ratingValueForMoney = ratingValueForMoney;
    }

    public float getRatingCleanliness() {
        return ratingCleanliness;
    }

    public void setRatingCleanliness(float ratingCleanliness) {
        this.ratingCleanliness = ratingCleanliness;
    }

    public float getRatingSecurity() {
        return ratingSecurity;
    }

    public void setRatingSecurity(float ratingSecurity) {
        this.ratingSecurity = ratingSecurity;
    }

    public float getRatingEnvironment() {
        return ratingEnvironment;
    }

    public void setRatingEnvironment(float ratingEnvironment) {
        this.ratingEnvironment = ratingEnvironment;
    }

    public float getRatingFacilities() {
        return ratingFacilities;
    }

    public void setRatingFacilities(float ratingFacilities) {
        this.ratingFacilities = ratingFacilities;
    }

    public float getRatingFood() {
        return ratingFood;
    }

    public void setRatingFood(float ratingFood) {
        this.ratingFood = ratingFood;
    }

    public float getRatingStaff() {
        return ratingStaff;
    }

    public void setRatingStaff(float ratingStaff) {
        this.ratingStaff = ratingStaff;
    }

    public float getRatingValueForMoney() {
        return ratingValueForMoney;
    }

    public void setRatingValueForMoney(float ratingValueForMoney) {
        this.ratingValueForMoney = ratingValueForMoney;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
