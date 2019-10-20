package com.wishill.wishill.api.recommendedColleges.getScholarshipDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class ScholarshipDetailsData {
    @SerializedName("basicDetail") @Expose private ScholarshipBasicDetailsData basicDetails;

    public ScholarshipBasicDetailsData getBasicDetails() {
        return basicDetails;
    }

    public void setBasicDetails(ScholarshipBasicDetailsData basicDetails) {
        this.basicDetails = basicDetails;
    }
}
