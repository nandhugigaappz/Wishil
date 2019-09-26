package com.wishill.wishill.api.recommendedColleges.examCategories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class ExamCategoriesResponse {
    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recomm") @Expose private List<ExamCategoriesListData> catList = new ArrayList<ExamCategoriesListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ExamCategoriesListData> getCatList() {
        return catList;
    }

    public void setCatList(List<ExamCategoriesListData> catList) {
        this.catList = catList;
    }
}
