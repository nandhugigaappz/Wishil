package com.wishill.wishill.api.recommendedColleges.storieslist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nandhu on 9/10/2016.
 */
public class StoriesListData implements Serializable{
    @SerializedName("blogId") @Expose private String blogId;
    @SerializedName("blogTitle") @Expose private String blogTitle;
    @SerializedName("blogImg") @Expose private String blogImg;

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogImg() {
        return blogImg;
    }

    public void setBlogImg(String blogImg) {
        this.blogImg = blogImg;
    }
}
