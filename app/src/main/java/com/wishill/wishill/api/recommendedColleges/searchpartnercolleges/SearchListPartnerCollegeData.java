package com.wishill.wishill.api.recommendedColleges.searchpartnercolleges;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by altoopa on 9/10/2016.
 */
public class SearchListPartnerCollegeData implements Serializable{
    @SerializedName("categoryID") @Expose private String categoryID;
    @SerializedName("itemID") @Expose private String itemID;
    @SerializedName("name") @Expose private String name;
    @SerializedName("itemImg") @Expose private String itemImg;
    @SerializedName("location") @Expose private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
