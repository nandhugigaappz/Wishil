package com.wishill.wishill.api.recommendedColleges.studyabroaddetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class StudyAbroadDetailsResponse {
    @SerializedName("recom") @Expose private StudyAbroadDetailsData dataList;

    public StudyAbroadDetailsData getDataList() {
        return dataList;
    }

    public void setDataList(StudyAbroadDetailsData dataList) {
        this.dataList = dataList;
    }
}
