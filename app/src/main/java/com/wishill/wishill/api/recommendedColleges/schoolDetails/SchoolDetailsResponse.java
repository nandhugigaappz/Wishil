package com.wishill.wishill.api.recommendedColleges.schoolDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SchoolDetailsResponse {
    @SerializedName("recom") @Expose private SchoolDetailsData detailsData;

    public SchoolDetailsData getDetailsData() {
        return detailsData;
    }

    public void setDetailsData(SchoolDetailsData detailsData) {
        this.detailsData = detailsData;
    }
}
