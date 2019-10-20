package com.wishill.wishill.api.recommendedColleges.internshipList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class InternShipData {
    @SerializedName("internshipID") @Expose private String internshipID;
    @SerializedName("title") @Expose private String title;
    @SerializedName("startDate") @Expose private String startDate;
    @SerializedName("duration") @Expose private String duration;
    @SerializedName("companyName") @Expose private String companyName;


    public String getInternshipID() {
        return internshipID;
    }

    public void setInternshipID(String internshipID) {
        this.internshipID = internshipID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
