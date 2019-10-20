package com.wishill.wishill.api.recommendedColleges.collegeCouseDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class CollegeCourseResponse {
    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recomm1") @Expose private List<CollegeCourseListData> courseList = new ArrayList<CollegeCourseListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CollegeCourseListData> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CollegeCourseListData> courseList) {
        this.courseList = courseList;
    }
}
