package com.wishill.wishill.api.recommendedColleges.getStudyTourProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class StudyTourProfileDetailsData {

    @SerializedName("logoimage") @Expose private String logoimage;
    @SerializedName("instname") @Expose private String instname;
    @SerializedName("usertype") @Expose private String usertype;
    @SerializedName("username") @Expose private String username;
    @SerializedName("email") @Expose private String email;
    @SerializedName("country") @Expose private String country;
    @SerializedName("mobilenum") @Expose private String mobilenum;
    @SerializedName("pstate") @Expose private String pstate;
    @SerializedName("pdistrict") @Expose private String pdistrict;
    @SerializedName("pcity") @Expose private String pcity;
    @SerializedName("pestyear") @Expose private String pestyear;
    @SerializedName("pabtinst") @Expose private String pabtinst;
    @SerializedName("studytourID") @Expose private String studytourID;

    public String getStudytourID() {
        return studytourID;
    }

    public void setStudytourID(String studytourID) {
        this.studytourID = studytourID;
    }

    public String getLogoimage() {
        return logoimage;
    }

    public void setLogoimage(String logoimage) {
        this.logoimage = logoimage;
    }

    public String getInstname() {
        return instname;
    }

    public void setInstname(String instname) {
        this.instname = instname;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public String getPstate() {
        return pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

    public String getPdistrict() {
        return pdistrict;
    }

    public void setPdistrict(String pdistrict) {
        this.pdistrict = pdistrict;
    }

    public String getPcity() {
        return pcity;
    }

    public void setPcity(String pcity) {
        this.pcity = pcity;
    }

    public String getPestyear() {
        return pestyear;
    }

    public void setPestyear(String pestyear) {
        this.pestyear = pestyear;
    }

    public String getPabtinst() {
        return pabtinst;
    }

    public void setPabtinst(String pabtinst) {
        this.pabtinst = pabtinst;
    }
}
