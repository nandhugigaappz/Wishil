package com.wishill.wishill.api.recommendedColleges.collegeCourseList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class CourseListData {
    @SerializedName("courseID") @Expose private String courseID;
    @SerializedName("courseName") @Expose private String courseName;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
