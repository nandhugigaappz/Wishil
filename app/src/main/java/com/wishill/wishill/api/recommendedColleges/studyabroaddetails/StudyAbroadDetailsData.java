package com.wishill.wishill.api.recommendedColleges.studyabroaddetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class StudyAbroadDetailsData {
    @SerializedName("basicDetail") @Expose private StudyAbroadBasicDetailsData basicDetail;
    @SerializedName("itemImage") @Expose private List<StudyAbroadImageDetails> imageDetails;
    @SerializedName("itemAmenties") @Expose private List<StudyAbroadAmenitiesDetailsData> amenitiesList=new ArrayList<>();
    @SerializedName("itemCourse") @Expose private List<StudyAbroadCoursesDetailsData> itemCourse=new ArrayList<>();


    @SerializedName("detailUrl") @Expose private String detailUrl;
    @SerializedName("amenityPath") @Expose private String amenityPath;
    @SerializedName("itemImgpath") @Expose private String itemImgpath;
    @SerializedName("logoImgpath") @Expose private String logoImgPath;
    @SerializedName("wishCount") @Expose private String wishCount;
    @SerializedName("followCount") @Expose private String followCount;
    @SerializedName("myFollowers") @Expose private String myFollowers;
    @SerializedName("refer") @Expose private String refer;


    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public List<StudyAbroadImageDetails> getImageDetails() {
        return imageDetails;
    }

    public void setImageDetails(List<StudyAbroadImageDetails> imageDetails) {
        this.imageDetails = imageDetails;
    }

    public StudyAbroadBasicDetailsData getBasicDetail() {
        return basicDetail;
    }

    public void setBasicDetail(StudyAbroadBasicDetailsData basicDetail) {
        this.basicDetail = basicDetail;
    }

    public List<StudyAbroadAmenitiesDetailsData> getAmenitiesList() {
        return amenitiesList;
    }

    public void setAmenitiesList(List<StudyAbroadAmenitiesDetailsData> amenitiesList) {
        this.amenitiesList = amenitiesList;
    }

    public List<StudyAbroadCoursesDetailsData> getItemCourse() {
        return itemCourse;
    }

    public void setItemCourse(List<StudyAbroadCoursesDetailsData> itemCourse) {
        this.itemCourse = itemCourse;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getAmenityPath() {
        return amenityPath;
    }

    public void setAmenityPath(String amenityPath) {
        this.amenityPath = amenityPath;
    }

    public String getItemImgpath() {
        return itemImgpath;
    }

    public void setItemImgpath(String itemImgpath) {
        this.itemImgpath = itemImgpath;
    }

    public String getLogoImgPath() {
        return logoImgPath;
    }

    public void setLogoImgPath(String logoImgPath) {
        this.logoImgPath = logoImgPath;
    }

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

    public String getMyFollowers() {
        return myFollowers;
    }

    public void setMyFollowers(String myFollowers) {
        this.myFollowers = myFollowers;
    }
}
