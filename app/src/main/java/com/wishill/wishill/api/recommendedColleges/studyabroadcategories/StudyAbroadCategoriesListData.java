package com.wishill.wishill.api.recommendedColleges.studyabroadcategories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class StudyAbroadCategoriesListData {
    @SerializedName("catgoryID") @Expose private String catgoryID;
    @SerializedName("countryID") @Expose private String countryID;
    @SerializedName("subcategoryID") @Expose private String subcategoryID;
    @SerializedName("categoryImg") @Expose private String categoryImg;
    @SerializedName("categoryname") @Expose private String categoryname;

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCatgoryID() {
        return catgoryID;
    }

    public void setCatgoryID(String catgoryID) {
        this.catgoryID = catgoryID;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getSubcategoryID() {
        return subcategoryID;
    }

    public void setSubcategoryID(String subcategoryID) {
        this.subcategoryID = subcategoryID;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }
}
