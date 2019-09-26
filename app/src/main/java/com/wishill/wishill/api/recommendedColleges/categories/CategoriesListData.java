package com.wishill.wishill.api.recommendedColleges.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class CategoriesListData {
    @SerializedName("wishsubcat_id") @Expose private String id;
    @SerializedName("wishsubcat_name") @Expose private String name;
    @SerializedName("categoryImg") @Expose private String categoryImg;

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
