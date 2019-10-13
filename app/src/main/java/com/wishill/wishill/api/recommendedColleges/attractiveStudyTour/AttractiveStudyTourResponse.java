package com.wishill.wishill.api.recommendedColleges.attractiveStudyTour;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class AttractiveStudyTourResponse {
    @SerializedName("recomm") @Expose private List<AttractiveStudyTourListData> attractiveStudyTourLIst = new ArrayList<AttractiveStudyTourListData>();

    public List<AttractiveStudyTourListData> getAttractiveStudyTourLIst() {
        return attractiveStudyTourLIst;
    }

    public void setAttractiveStudyTourLIst(List<AttractiveStudyTourListData> attractiveStudyTourLIst) {
        this.attractiveStudyTourLIst = attractiveStudyTourLIst;
    }
}
