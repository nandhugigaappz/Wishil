package com.wishill.wishill.api.recommendedColleges.SchoolFess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class SchoolFeesResponse {
    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recomm") @Expose private List<SchoolFeesListData> feesList = new ArrayList<SchoolFeesListData>();

    public List<SchoolFeesListData> getFeesList() {
        return feesList;
    }

    public void setFeesList(List<SchoolFeesListData> feesList) {
        this.feesList = feesList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
