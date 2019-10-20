package com.wishill.wishill.api.recommendedColleges.getStudyTourCat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class StudyTourCatListResponse {
    @SerializedName("recomm") @Expose private List<StudyTourCatListData> catList = new ArrayList<StudyTourCatListData>();

    public List<StudyTourCatListData> getCatList() {
        return catList;
    }

    public void setCatList(List<StudyTourCatListData> catList) {
        this.catList = catList;
    }
}
