package com.wishill.wishill.api.recommendedColleges.schoolDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SchoolBasicDetailsData {
    @SerializedName("school_name") @Expose private String schoolName;
    @SerializedName("school_city") @Expose private String schoolCity;
    @SerializedName("school_logo") @Expose private String schoolLogo;
    @SerializedName("school_cover") @Expose private String schoolCover;
    @SerializedName("school_desc") @Expose private String schoolDesc;
    @SerializedName("school_estyear") @Expose private String schoolEstYear;
    @SerializedName("school_affil") @Expose private String schoolAffil;
    @SerializedName("education_offedred") @Expose private String educationOffered;
    @SerializedName("labs") @Expose private String labs;
    @SerializedName("nearby") @Expose private String nearby;
    @SerializedName("sports") @Expose private String sports;
    @SerializedName("tel") @Expose private String tel;
    @SerializedName("email") @Expose private String email;
    @SerializedName("website") @Expose private String website;
    @SerializedName("lattitude") @Expose private String lattitude;
    @SerializedName("longitude") @Expose private String longitude;

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }

    public String getNearby() {
        return nearby;
    }

    public void setNearby(String nearby) {
        this.nearby = nearby;
    }

    public String getLabs() {
        return labs;
    }

    public void setLabs(String labs) {
        this.labs = labs;
    }

    public String getEducationOffered() {
        return educationOffered;
    }

    public void setEducationOffered(String educationOffered) {
        this.educationOffered = educationOffered;
    }

    public String getSchoolAffil() {
        return schoolAffil;
    }

    public void setSchoolAffil(String schoolAffil) {
        this.schoolAffil = schoolAffil;
    }

    public String getSchoolEstYear() {
        return schoolEstYear;
    }

    public void setSchoolEstYear(String schoolEstYear) {
        this.schoolEstYear = schoolEstYear;
    }

    public String getSchoolDesc() {
        return schoolDesc;
    }

    public void setSchoolDesc(String schoolDesc) {
        this.schoolDesc = schoolDesc;
    }

    public String getSchoolLogo() {
        return schoolLogo;
    }

    public void setSchoolLogo(String schoolLogo) {
        this.schoolLogo = schoolLogo;
    }

    public String getSchoolCover() {
        return schoolCover;
    }

    public void setSchoolCover(String schoolCover) {
        this.schoolCover = schoolCover;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }
}
