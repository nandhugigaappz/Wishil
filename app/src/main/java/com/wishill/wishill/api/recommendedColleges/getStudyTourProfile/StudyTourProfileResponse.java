package com.wishill.wishill.api.recommendedColleges.getStudyTourProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class StudyTourProfileResponse {
    @SerializedName("recomm") @Expose private List<StudyTourProfileDetailsData> detailsList = new ArrayList<StudyTourProfileDetailsData>();
    @SerializedName("success") @Expose private Integer success;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<StudyTourProfileDetailsData> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<StudyTourProfileDetailsData> detailsList) {
        this.detailsList = detailsList;
    }
}
