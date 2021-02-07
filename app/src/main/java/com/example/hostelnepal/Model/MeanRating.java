package com.example.hostelnepal.Model;

public class MeanRating {
    private float meanCleanliness,meanSecurity,meanFood,meanStaff,meanEnvironment,
                    meanFacilities,meanValueForMoney,count;


    public MeanRating() {
        //no argument constructor
    }

    public MeanRating(float meanCleanliness,
                      float meanSecurity,
                      float meanFood,
                      float meanStaff,
                      float meanEnvironment,
                      float meanFacilities,
                      float meanValueForMoney,
                      float count) {

        this.meanCleanliness = meanCleanliness;
        this.meanSecurity = meanSecurity;
        this.meanFood = meanFood;
        this.meanStaff = meanStaff;
        this.meanEnvironment = meanEnvironment;
        this.meanFacilities = meanFacilities;
        this.meanValueForMoney = meanValueForMoney;
        this.count = count;

    }

    public float getMeanCleanliness() {
        return meanCleanliness;
    }

    public void setMeanCleanliness(float meanCleanliness) {
        this.meanCleanliness = meanCleanliness;
    }

    public float getMeanSecurity() {
        return meanSecurity;
    }

    public void setMeanSecurity(float meanSecurity) {
        this.meanSecurity = meanSecurity;
    }

    public float getMeanFood() {
        return meanFood;
    }

    public void setMeanFood(float meanFood) {
        this.meanFood = meanFood;
    }

    public float getMeanStaff() {
        return meanStaff;
    }

    public void setMeanStaff(float meanStaff) {
        this.meanStaff = meanStaff;
    }

    public float getMeanEnvironment() {
        return meanEnvironment;
    }

    public void setMeanEnvironment(float meanEnvironment) {
        this.meanEnvironment = meanEnvironment;
    }

    public float getMeanFacilities() {
        return meanFacilities;
    }

    public void setMeanFacilities(float meanFacilities) {
        this.meanFacilities = meanFacilities;
    }

    public float getMeanValueForMoney() {
        return meanValueForMoney;
    }

    public void setMeanValueForMoney(float meanValueForMoney) {
        this.meanValueForMoney = meanValueForMoney;
    }
    public void setCount(float count){
        this.count = count;
    }
    public float getCount(){
        return this.count;
    }
}
