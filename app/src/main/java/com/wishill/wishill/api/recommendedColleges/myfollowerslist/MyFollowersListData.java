package com.wishill.wishill.api.recommendedColleges.myfollowerslist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by altoopa on 9/10/2016.
 */
public class MyFollowersListData implements Serializable{
    @SerializedName("name") @Expose private String name;
    @SerializedName("user_image") @Expose private String user_image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
