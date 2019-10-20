package com.wishill.wishill.api.recommendedColleges.examList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nandhu on 9/10/2016.
 */
public class ExamListData implements Serializable{
    @SerializedName("examttl") @Expose private String examTitle;
    @SerializedName("exammd") @Expose private String examMode;
    @SerializedName("examregdt") @Expose private String regDate;
    @SerializedName("examcrsofr") @Expose private String courseOffered;
    @SerializedName("examcrslvl") @Expose private String courseLevel;
    @SerializedName("examdur") @Expose private String examDuration;
    @SerializedName("exammedm") @Expose private String examMedium;
    @SerializedName("examtqn") @Expose private String totalQuestion;
    @SerializedName("examtmrk") @Expose private String totalMark;
    @SerializedName("examhdp") @Expose private String mobile;
    @SerializedName("examweb") @Expose private String website;
    @SerializedName("exameml") @Expose private String email;
    @SerializedName("examdesc") @Expose private String description;
    @SerializedName("examtcty") @Expose private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCourseOffered() {
        return courseOffered;
    }

    public void setCourseOffered(String courseOffered) {
        this.courseOffered = courseOffered;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(String examDuration) {
        this.examDuration = examDuration;
    }

    public String getExamMedium() {
        return examMedium;
    }

    public void setExamMedium(String examMedium) {
        this.examMedium = examMedium;
    }

    public String getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public String getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(String totalMark) {
        this.totalMark = totalMark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExamMode() {
        return examMode;
    }

    public void setExamMode(String examMode) {
        this.examMode = examMode;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }
}
