package com.wishill.wishill.api.recommendedColleges.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class CategoriesResponse {
    @SerializedName("success") @Expose private Integer status;
    @SerializedName("img_url") @Expose private String imageUrl;
    @SerializedName("recom") @Expose private List<CategoriesListData> catList = new ArrayList<CategoriesListData>();

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CategoriesListData> getCatList() {
        return catList;
    }

    public void setCatList(List<CategoriesListData> catList) {
        this.catList = catList;
    }
}
