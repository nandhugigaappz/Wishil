package com.wishill.wishill.api.recommendedColleges.getScholarshipDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class ScholarshipDetailsResponse {
    @SerializedName("recom") @Expose private ScholarshipDetailsData details;

    public ScholarshipDetailsData getDetails() {
        return details;
    }

    public void setDetails(ScholarshipDetailsData details) {
        this.details = details;
    }
}
