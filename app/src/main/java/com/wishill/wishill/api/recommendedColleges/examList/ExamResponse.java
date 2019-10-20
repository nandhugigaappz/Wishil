package com.wishill.wishill.api.recommendedColleges.examList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class ExamResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recomm") @Expose private List<ExamListData> catList = new ArrayList<ExamListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ExamListData> getCatList() {
        return catList;
    }

    public void setCatList(List<ExamListData> catList) {
        this.catList = catList;
    }
}
