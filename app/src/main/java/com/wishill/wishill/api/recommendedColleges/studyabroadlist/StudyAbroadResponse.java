package com.wishill.wishill.api.recommendedColleges.studyabroadlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class StudyAbroadResponse {

    @SerializedName("success") @Expose private String status;
    @SerializedName("message") @Expose private String message;
    @SerializedName("recom") @Expose private List<StudyAbroadListData> studyAbroadList = new ArrayList<StudyAbroadListData>();

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

    public List<StudyAbroadListData> getStudyAbroadList() {
        return studyAbroadList;
    }

    public void setStudyAbroadList(List<StudyAbroadListData> studyAbroadList) {
        this.studyAbroadList = studyAbroadList;
    }
}
