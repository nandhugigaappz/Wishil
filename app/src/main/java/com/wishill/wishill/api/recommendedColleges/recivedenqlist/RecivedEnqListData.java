package com.wishill.wishill.api.recommendedColleges.recivedenqlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nandhu on 9/10/2016.
 */
public class RecivedEnqListData implements Serializable{
    @SerializedName("enqID") @Expose private String enqID;
    @SerializedName("enqContent") @Expose private String enqContent;
    @SerializedName("enqUserName") @Expose private String enqUserName;
    @SerializedName("userImage") @Expose private String userImage;

    public String getEnqUserName() {
        return enqUserName;
    }

    public void setEnqUserName(String enqUserName) {
        this.enqUserName = enqUserName;
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

    public String getEnqContent() {
        return enqContent;
    }

    public void setEnqContent(String enqContent) {
        this.enqContent = enqContent;
    }
}
