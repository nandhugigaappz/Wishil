package com.wishill.wishill.api.recommendedColleges.studyabroadcategories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class StudyAbroadCategoriesResponse {
    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private List<StudyAbroadCategoriesListData> catList = new ArrayList<StudyAbroadCategoriesListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StudyAbroadCategoriesListData> getCatList() {
        return catList;
    }

    public void setCatList(List<StudyAbroadCategoriesListData> catList) {
        this.catList = catList;
    }
}
