package com.wishill.wishill.api.recommendedColleges.getInternshipDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class InternshipDetailsData {
    @SerializedName("basicDetail") @Expose private InternshipBasicDetailsData internshipBasicDetailsData;

    public InternshipBasicDetailsData getInternshipBasicDetailsData() {
        return internshipBasicDetailsData;
    }

    public void setInternshipBasicDetailsData(InternshipBasicDetailsData internshipBasicDetailsData) {
        this.internshipBasicDetailsData = internshipBasicDetailsData;
    }
}
