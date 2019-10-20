package com.wishill.wishill.api.recommendedColleges.mywishlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nandhu on 9/10/2016.
 */
public class MyWishListData implements Serializable{
    @SerializedName("name") @Expose private String name;
    @SerializedName("image") @Expose private String image;
    @SerializedName("typeID") @Expose private String typeID;
    @SerializedName("itemID") @Expose private String itemID;
    @SerializedName("city") @Expose private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}
