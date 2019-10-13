package com.wishill.wishill.api.recommendedColleges.collegeCouseDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class CollegeCourseListData {
    @SerializedName("mdstudy") @Expose private String heading;
    @SerializedName("total_fees") @Expose private String totalFees;
    @SerializedName("1st_year") @Expose private String firstYear;
    @SerializedName("2nd_year") @Expose private String secondYear;
    @SerializedName("3rd_year") @Expose private String thirdYear;
    @SerializedName("4th_year") @Expose private String fourthYear;
    @SerializedName("5th_year") @Expose private String fifthYear;
    @SerializedName("seats") @Expose private String seats;
    @SerializedName("elgibility") @Expose private String eligibility;
    @SerializedName("aprocedure") @Expose private String admissionProcedure;
    @SerializedName("pdetails") @Expose private String procedureDetails;
    @SerializedName("sdetails") @Expose private String scholarshipDetails;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(String totalFees) {
        this.totalFees = totalFees;
    }

    public String getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(String firstYear) {
        this.firstYear = firstYear;
    }

    public String getSecondYear() {
        return secondYear;
    }

    public void setSecondYear(String secondYear) {
        this.secondYear = secondYear;
    }

    public String getThirdYear() {
        return thirdYear;
    }

    public void setThirdYear(String thirdYear) {
        this.thirdYear = thirdYear;
    }

    public String getFourthYear() {
        return fourthYear;
    }

    public void setFourthYear(String fourthYear) {
        this.fourthYear = fourthYear;
    }

    public String getFifthYear() {
        return fifthYear;
    }

    public void setFifthYear(String fifthYear) {
        this.fifthYear = fifthYear;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getAdmissionProcedure() {
        return admissionProcedure;
    }

    public void setAdmissionProcedure(String admissionProcedure) {
        this.admissionProcedure = admissionProcedure;
    }

    public String getProcedureDetails() {
        return procedureDetails;
    }

    public void setProcedureDetails(String procedureDetails) {
        this.procedureDetails = procedureDetails;
    }

    public String getScholarshipDetails() {
        return scholarshipDetails;
    }

    public void setScholarshipDetails(String scholarshipDetails) {
        this.scholarshipDetails = scholarshipDetails;
    }
}
