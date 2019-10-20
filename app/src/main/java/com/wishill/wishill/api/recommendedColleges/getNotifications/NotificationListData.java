package com.wishill.wishill.api.recommendedColleges.getNotifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nandhu on 9/10/2016.
 */
public class NotificationListData implements Serializable{
    @SerializedName("typeName") @Expose private String typeName;
    @SerializedName("itemName") @Expose private String itemName;
    @SerializedName("city") @Expose private String city;
    @SerializedName("content") @Expose private String content;
    @SerializedName("image") @Expose private String image;
    @SerializedName("postedDate") @Expose private String postedDate;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }
}
