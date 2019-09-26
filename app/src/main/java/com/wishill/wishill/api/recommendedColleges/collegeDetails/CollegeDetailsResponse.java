package com.wishill.wishill.api.recommendedColleges.collegeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class CollegeDetailsResponse {
    @SerializedName("recom") @Expose private DetailsData dataList;

    public DetailsData getDataList() {
        return dataList;
    }

    public void setDataList(DetailsData dataList) {
        this.dataList = dataList;
    }
}
