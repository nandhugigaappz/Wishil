package com.wishill.wishill.api.recommendedColleges.getInternshipDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class InternshipDetailsResponse {
    @SerializedName("recom") @Expose private InternshipDetailsData detailsData;

    public InternshipDetailsData getDetailsData() {
        return detailsData;
    }

    public void setDetailsData(InternshipDetailsData detailsData) {
        this.detailsData = detailsData;
    }
}
