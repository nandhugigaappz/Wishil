package com.wishill.wishill.api.recommendedColleges.sendenqlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SendEnqListData implements Serializable{
    @SerializedName("enqID") @Expose private String enqID;
    @SerializedName("enqContent") @Expose private String enqContent;
    @SerializedName("collegeImg") @Expose private String collegeImg;
    @SerializedName("itemName") @Expose private String itemName;


    public String getCollegeImg() {
        return collegeImg;
    }

    public void setCollegeImg(String collegeImg) {
        this.collegeImg = collegeImg;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getEnqID() {
        return enqID;
    }

    public void setEnqID(String enqID) {
        this.enqID = enqID;
    }

    public String getEnqContent() {
        return enqContent;
    }

    public void setEnqContent(String enqContent) {
        this.enqContent = enqContent;
    }
}
