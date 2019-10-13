package com.wishill.wishill.api.recommendedColleges.subCatCollegeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class SubCatCollegeListData {
    @SerializedName("collegeID") @Expose private String collegeID;
    @SerializedName("collegeName") @Expose private String collegeName;
    @SerializedName("cp_contact") @Expose private String contact;
    @SerializedName("e_year") @Expose private String year;
    @SerializedName("city") @Expose private String city;
    @SerializedName("college_img") @Expose private String college_img;
    @SerializedName("phone") @Expose private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCollege_img() {
        return college_img;
    }

    public void setCollege_img(String college_img) {
        this.college_img = college_img;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCollegeID() {
        return collegeID;
    }

    public void setCollegeID(String collegeID) {
        this.collegeID = collegeID;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }


}
