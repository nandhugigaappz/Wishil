package com.wishill.wishill.api.recommendedColleges.studyabroaddetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class StudyAbroadDetailsResponse {
    @SerializedName("success") @Expose private String status;
    @SerializedName("message") @Expose private String message;
    @SerializedName("recom") @Expose private StudyAbroadDetailsData dataList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StudyAbroadDetailsData getDataList() {
        return dataList;
    }

    public void setDataList(StudyAbroadDetailsData dataList) {
        this.dataList = dataList;
    }
}
