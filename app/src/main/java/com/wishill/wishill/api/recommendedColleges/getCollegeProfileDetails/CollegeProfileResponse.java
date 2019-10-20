package com.wishill.wishill.api.recommendedColleges.getCollegeProfileDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class CollegeProfileResponse {
    @SerializedName("recomm") @Expose private List<CollegeDetailsData> detailsList = new ArrayList<CollegeDetailsData>();
    @SerializedName("success") @Expose private Integer success;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<CollegeDetailsData> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<CollegeDetailsData> detailsList) {
        this.detailsList = detailsList;
    }
}
