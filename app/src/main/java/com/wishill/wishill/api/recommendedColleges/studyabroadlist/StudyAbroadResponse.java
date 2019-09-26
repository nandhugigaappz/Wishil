package com.wishill.wishill.api.recommendedColleges.studyabroadlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class StudyAbroadResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private List<StudyAbroadListData> studyAbroadList = new ArrayList<StudyAbroadListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StudyAbroadListData> getStudyAbroadList() {
        return studyAbroadList;
    }

    public void setStudyAbroadList(List<StudyAbroadListData> studyAbroadList) {
        this.studyAbroadList = studyAbroadList;
    }
}
