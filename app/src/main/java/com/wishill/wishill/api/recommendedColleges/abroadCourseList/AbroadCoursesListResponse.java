package com.wishill.wishill.api.recommendedColleges.abroadCourseList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class AbroadCoursesListResponse {
    @SerializedName("recom") @Expose private List<AbroadCoursesListData> courseList = new ArrayList<AbroadCoursesListData>();

    public List<AbroadCoursesListData> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<AbroadCoursesListData> courseList) {
        this.courseList = courseList;
    }
}
