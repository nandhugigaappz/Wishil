package com.wishill.wishill.api.recommendedColleges.collegeCourseList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class CourseListResponse {
    @SerializedName("recom") @Expose private List<CourseListData> catList = new ArrayList<CourseListData>();

    public List<CourseListData> getCatList() {
        return catList;
    }

    public void setCatList(List<CourseListData> catList) {
        this.catList = catList;
    }
}
