package com.wishill.wishill.api.recommendedColleges.fullCategories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class FullCategoriesListData {
    @SerializedName("wishsubcat_id") @Expose private String wishsubcat_id;
    @SerializedName("wishsubcat_name") @Expose private String wishsubcat_name;
    @SerializedName("college_img") @Expose private String college_img;

    public String getWishsubcat_id() {
        return wishsubcat_id;
    }

    public void setWishsubcat_id(String wishsubcat_id) {
        this.wishsubcat_id = wishsubcat_id;
    }

    public String getWishsubcat_name() {
        return wishsubcat_name;
    }

    public void setWishsubcat_name(String wishsubcat_name) {
        this.wishsubcat_name = wishsubcat_name;
    }

    public String getCollege_img() {
        return college_img;
    }

    public void setCollege_img(String college_img) {
        this.college_img = college_img;
    }
}
