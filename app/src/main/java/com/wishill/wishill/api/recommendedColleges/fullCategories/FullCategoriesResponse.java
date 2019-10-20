package com.wishill.wishill.api.recommendedColleges.fullCategories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class FullCategoriesResponse {
    @SerializedName("recom") @Expose private List<FullCategoriesListData> catList = new ArrayList<FullCategoriesListData>();

    public List<FullCategoriesListData> getCatList() {
        return catList;
    }

    public void setCatList(List<FullCategoriesListData> catList) {
        this.catList = catList;
    }
}
