package com.wishill.wishill.api.recommendedColleges.studyTourList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class StudyTourListData {
    @SerializedName("packid") @Expose private String packid;
    @SerializedName("studytourID") @Expose private String studytourID;
    @SerializedName("pkgimg") @Expose private String pkgimg;
    @SerializedName("categoryid") @Expose private String categoryid;
    @SerializedName("pkgname") @Expose private String pkgname;
    @SerializedName("destin") @Expose private String destin;
    @SerializedName("country") @Expose private String country;
    @SerializedName("pkgrate") @Expose private String pkgrate;

    public String getPackid() {
        return packid;
    }

    public void setPackid(String packid) {
        this.packid = packid;
    }

    public String getStudytourID() {
        return studytourID;
    }

    public void setStudytourID(String studytourID) {
        this.studytourID = studytourID;
    }

    public String getPkgimg() {
        return pkgimg;
    }

    public void setPkgimg(String pkgimg) {
        this.pkgimg = pkgimg;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getPkgname() {
        return pkgname;
    }

    public void setPkgname(String pkgname) {
        this.pkgname = pkgname;
    }

    public String getDestin() {
        return destin;
    }

    public void setDestin(String destin) {
        this.destin = destin;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPkgrate() {
        return pkgrate;
    }

    public void setPkgrate(String pkgrate) {
        this.pkgrate = pkgrate;
    }
}
