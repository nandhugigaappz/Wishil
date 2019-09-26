package com.wishill.wishill.api.recommendedColleges.enqdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class EnqDetailsData {
    @SerializedName("collegeImg") @Expose private String collegeImg;
    @SerializedName("userImage") @Expose private String userImage;
    @SerializedName("enqID") @Expose private String enqID;
    @SerializedName("enqTypeID") @Expose private String enqTypeID;
    @SerializedName("enqItemID") @Expose private String enqItemID;
    @SerializedName("enqContent") @Expose private String enqContent;
    @SerializedName("itemName") @Expose private String itemName;
    @SerializedName("enqUserName") @Expose private String enqUserName;
    @SerializedName("enqUserEmail") @Expose private String enqUserEmail;
    @SerializedName("enqUserPhone") @Expose private String enqUserPhone;

    public String getEnqUserName() {
        return enqUserName;
    }

    public void setEnqUserName(String enqUserName) {
        this.enqUserName = enqUserName;
    }

    public String getEnqUserEmail() {
        return enqUserEmail;
    }

    public void setEnqUserEmail(String enqUserEmail) {
        this.enqUserEmail = enqUserEmail;
    }

    public String getEnqUserPhone() {
        return enqUserPhone;
    }

    public void setEnqUserPhone(String enqUserPhone) {
        this.enqUserPhone = enqUserPhone;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCollegeImg() {
        return collegeImg;
    }

    public void setCollegeImg(String collegeImg) {
        this.collegeImg = collegeImg;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getEnqID() {
        return enqID;
    }

    public void setEnqID(String enqID) {
        this.enqID = enqID;
    }

    public String getEnqTypeID() {
        return enqTypeID;
    }

    public void setEnqTypeID(String enqTypeID) {
        this.enqTypeID = enqTypeID;
    }

    public String getEnqItemID() {
        return enqItemID;
    }

    public void setEnqItemID(String enqItemID) {
        this.enqItemID = enqItemID;
    }

    public String getEnqContent() {
        return enqContent;
    }

    public void setEnqContent(String enqContent) {
        this.enqContent = enqContent;
    }
}
