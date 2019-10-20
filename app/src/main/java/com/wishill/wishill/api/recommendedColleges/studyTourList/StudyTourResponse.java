package com.wishill.wishill.api.recommendedColleges.studyTourList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class StudyTourResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recomm") @Expose private List<StudyTourListData> studyTourList = new ArrayList<StudyTourListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StudyTourListData> getStudyTourList() {
        return studyTourList;
    }

    public void setStudyTourList(List<StudyTourListData> studyTourList) {
        this.studyTourList = studyTourList;
    }
}
