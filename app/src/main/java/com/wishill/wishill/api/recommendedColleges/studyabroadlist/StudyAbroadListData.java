package com.wishill.wishill.api.recommendedColleges.studyabroadlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudyAbroadListData {
    @SerializedName("studyabroadID") @Expose private String studyabroadID;
    @SerializedName("collegeName") @Expose private String collegeName;
    @SerializedName("coverimage") @Expose private String coverimage;
    @SerializedName("country") @Expose private String country;
    @SerializedName("cp_contact") @Expose private String cp_contact;
    @SerializedName("phone") @Expose private String phone;
    @SerializedName("e_year") @Expose private String e_year;
    @SerializedName("college_img") @Expose private String college_img;

    public String getStudyabroadID() {
        return studyabroadID;
    }

    public void setStudyabroadID(String studyabroadID) {
        this.studyabroadID = studyabroadID;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCp_contact() {
        return cp_contact;
    }

    public void setCp_contact(String cp_contact) {
        this.cp_contact = cp_contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getE_year() {
        return e_year;
    }

    public void setE_year(String e_year) {
        this.e_year = e_year;
    }

    public String getCollege_img() {
        return college_img;
    }

    public void setCollege_img(String college_img) {
        this.college_img = college_img;
    }
}
