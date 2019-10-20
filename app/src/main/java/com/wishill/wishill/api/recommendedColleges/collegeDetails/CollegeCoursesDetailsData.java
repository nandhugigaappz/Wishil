package com.wishill.wishill.api.recommendedColleges.collegeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class CollegeCoursesDetailsData {
    @SerializedName("courseName") @Expose private String courseName;
    @SerializedName("fees") @Expose private String fees;
    @SerializedName("duration") @Expose private String duration;
    @SerializedName("mode") @Expose private String mode;
    @SerializedName("total_seats") @Expose private String total_seats;
    @SerializedName("eligibility") @Expose private String eligibility;

    public String getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(String total_seats) {
        this.total_seats = total_seats;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
