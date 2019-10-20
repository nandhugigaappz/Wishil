package com.wishill.wishill.api.recommendedColleges.getCollegeVideo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class CollegeVideoListData {
    @SerializedName("video") @Expose private String video;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
