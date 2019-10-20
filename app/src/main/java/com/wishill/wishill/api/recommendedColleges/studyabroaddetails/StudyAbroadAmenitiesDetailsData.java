package com.wishill.wishill.api.recommendedColleges.studyabroaddetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class StudyAbroadAmenitiesDetailsData {
    @SerializedName("amenityID") @Expose private String amenityID;
    @SerializedName("categoryID") @Expose private String categoryID;
    @SerializedName("name") @Expose private String name;
    @SerializedName("image") @Expose private String image;

    public String getAmenityID() {
        return amenityID;
    }

    public void setAmenityID(String amenityID) {
        this.amenityID = amenityID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
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
}
