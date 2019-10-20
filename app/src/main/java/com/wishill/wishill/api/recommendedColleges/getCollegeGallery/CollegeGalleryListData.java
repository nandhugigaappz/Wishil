package com.wishill.wishill.api.recommendedColleges.getCollegeGallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class CollegeGalleryListData {
    @SerializedName("image") @Expose private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
