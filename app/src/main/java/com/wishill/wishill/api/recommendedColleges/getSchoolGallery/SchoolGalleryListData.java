package com.wishill.wishill.api.recommendedColleges.getSchoolGallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class SchoolGalleryListData {
    @SerializedName("image") @Expose private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
