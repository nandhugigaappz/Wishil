package com.wishill.wishill.api.recommendedColleges.getStudyTourCat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class StudyTourCatListData {
    @SerializedName("categoryid") @Expose private String categoryId;
    @SerializedName("packcategoryname") @Expose private String packcategoryname;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPackcategoryname() {
        return packcategoryname;
    }

    public void setPackcategoryname(String packcategoryname) {
        this.packcategoryname = packcategoryname;
    }
}
