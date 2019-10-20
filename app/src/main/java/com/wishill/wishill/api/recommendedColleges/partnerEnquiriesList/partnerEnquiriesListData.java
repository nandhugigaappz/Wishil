package com.wishill.wishill.api.recommendedColleges.partnerEnquiriesList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class partnerEnquiriesListData {
    @SerializedName("en_date") @Expose private String enDate;
    @SerializedName("en_username") @Expose private String enUserName;
    @SerializedName("en_mail") @Expose private String enEmail;
    @SerializedName("en_usemob") @Expose private String enMobile;
    @SerializedName("en_edu") @Expose private String enEdu;
    @SerializedName("en_year") @Expose private String enYear;

    public String getEnDate() {
        return enDate;
    }

    public void setEnDate(String enDate) {
        this.enDate = enDate;
    }

    public String getEnUserName() {
        return enUserName;
    }

    public void setEnUserName(String enUserName) {
        this.enUserName = enUserName;
    }

    public String getEnEmail() {
        return enEmail;
    }

    public void setEnEmail(String enEmail) {
        this.enEmail = enEmail;
    }

    public String getEnMobile() {
        return enMobile;
    }

    public void setEnMobile(String enMobile) {
        this.enMobile = enMobile;
    }

    public String getEnEdu() {
        return enEdu;
    }

    public void setEnEdu(String enEdu) {
        this.enEdu = enEdu;
    }

    public String getEnYear() {
        return enYear;
    }

    public void setEnYear(String enYear) {
        this.enYear = enYear;
    }
}
