package com.wishill.wishill.api.recommendedColleges.collegeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class DetailsData {
    @SerializedName("basicDetail") @Expose private BasicDetailsData basicDetail;
    @SerializedName("collegeImgpath") @Expose private String collegeImgPath;
    @SerializedName("amenityPath") @Expose private String amenityPath;
    @SerializedName("logoImgpath") @Expose private String logoImgPath;
    @SerializedName("wishCount") @Expose private String wishCount;
    @SerializedName("followCount") @Expose private String followCount;
    @SerializedName("myFollowers") @Expose private String myFollowers;
    @SerializedName("detailUrl") @Expose private String detailUrl;
    @SerializedName("recomonded") @Expose private int refer;

    public String getAmenityPath() {
        return amenityPath;
    }

    public void setAmenityPath(String amenityPath) {
        this.amenityPath = amenityPath;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getMyFollowers() {
        return myFollowers;
    }

    public void setMyFollowers(String myFollowers) {
        this.myFollowers = myFollowers;
    }

    @SerializedName("collegeAmenties") @Expose private List<CollegeAmenitiesDetailsData> amenitiesList=new ArrayList<>();
    @SerializedName("collegeCourse") @Expose private List<CollegeCoursesDetailsData> collegeCourseList=new ArrayList<>();

    public String getWishCount() {
        return wishCount;
    }

    public void setWishCount(String wishCount) {
        this.wishCount = wishCount;
    }

    public String getFollowCount() {
        return followCount;
    }

    public void setFollowCount(String followCount) {
        this.followCount = followCount;
    }

    public String getCollegeImgPath() {
        return collegeImgPath;
    }

    public void setCollegeImgPath(String collegeImgPath) {
        this.collegeImgPath = collegeImgPath;
    }

    public String getLogoImgPath() {
        return logoImgPath;
    }

    public void setLogoImgPath(String logoImgPath) {
        this.logoImgPath = logoImgPath;
    }

    public List<CollegeCoursesDetailsData> getCollegeCourseList() {
        return collegeCourseList;
    }

    public void setCollegeCourseList(List<CollegeCoursesDetailsData> collegeCourseList) {
        this.collegeCourseList = collegeCourseList;
    }

    public List<CollegeAmenitiesDetailsData> getAmenitiesList() {
        return amenitiesList;
    }

    public void setAmenitiesList(List<CollegeAmenitiesDetailsData> amenitiesList) {
        this.amenitiesList = amenitiesList;
    }

    public BasicDetailsData getBasicDetail() {
        return basicDetail;
    }

    public void setBasicDetail(BasicDetailsData basicDetail) {
        this.basicDetail = basicDetail;
    }

    public int getRefer() {
        return refer;
    }

    public void setRefer(int refer) {
        this.refer = refer;
    }
}
