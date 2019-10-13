package com.wishill.wishill.api.recommendedColleges.studyabroaddetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudyAbroadImageDetails {
    @SerializedName("image") @Expose private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
