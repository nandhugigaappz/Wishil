package com.wishill.wishill.api.recommendedColleges.getSchoolProfileDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SchoolProfileResponse {
    @SerializedName("recomm") @Expose private List<SchoolProfileDetailsData> detailsList = new ArrayList<SchoolProfileDetailsData>();
    @SerializedName("success") @Expose private Integer success;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<SchoolProfileDetailsData> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<SchoolProfileDetailsData> detailsList) {
        this.detailsList = detailsList;
    }
}
