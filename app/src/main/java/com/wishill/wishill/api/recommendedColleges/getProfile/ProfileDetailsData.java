package com.wishill.wishill.api.recommendedColleges.getProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by altoopa on 9/10/2016.
 */
public class ProfileDetailsData implements Serializable {
    @SerializedName("user_email") @Expose private String userEmail;
    @SerializedName("user_mob") @Expose private String userMobile;
    @SerializedName("firstname") @Expose private String firstName;
    @SerializedName("lastname") @Expose private String lastName;
    @SerializedName("user_image") @Expose private String userImage;
    @SerializedName("address") @Expose private String address;
    @SerializedName("city") @Expose private String city;
    @SerializedName("gender") @Expose private String gender;
    @SerializedName("dateofbirth") @Expose private String dateofbirth;
    @SerializedName("heighestQualication") @Expose private String heighestQualication;
    @SerializedName("institution") @Expose private String institution;
    @SerializedName("mark") @Expose private String mark;
    @SerializedName("state") @Expose private String state;
    @SerializedName("followCount") @Expose private String followCount;
    @SerializedName("enquiryCount") @Expose private String enquiryCount;
    @SerializedName("receivedfollowCount") @Expose private String receivedfollowCount;
    @SerializedName("receivedenquiryCount") @Expose private String receivedenquiryCount;
    @SerializedName("institutionID") @Expose private String institutionID;
    @SerializedName("institutionStatus") @Expose private String institutionStatus;
    @SerializedName("userLoginType") @Expose private String userLoginType;
    @SerializedName("social_userImg") @Expose private String social_userImg;

    public String getUserLoginType() {
        return userLoginType;
    }

    public void setUserLoginType(String userLoginType) {
        this.userLoginType = userLoginType;
    }

    public String getSocial_userImg() {
        return social_userImg;
    }

    public void setSocial_userImg(String social_userImg) {
        this.social_userImg = social_userImg;
    }

    public String getInstitutionStatus() {
        return institutionStatus;
    }

    public void setInstitutionStatus(String institutionStatus) {
        this.institutionStatus = institutionStatus;
    }

    public String getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(String institutionID) {
        this.institutionID = institutionID;
    }

    public String getReceivedfollowCount(){
        return receivedfollowCount;
    }

    public void setReceivedfollowCount(String receivedfollowCount) {
        this.receivedfollowCount = receivedfollowCount;
    }

    public String getReceivedenquiryCount() {
        return receivedenquiryCount;
    }

    public void setReceivedenquiryCount(String receivedenquiryCount) {
        this.receivedenquiryCount = receivedenquiryCount;
    }

    public String getEnquiryCount() {
        return enquiryCount;
    }

    public void setEnquiryCount(String enquiryCount) {
        this.enquiryCount = enquiryCount;
    }

    public String getFollowCount() {
        return followCount;
    }

    public void setFollowCount(String followCount) {
        this.followCount = followCount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getHeighestQualication() {
        return heighestQualication;
    }

    public void setHeighestQualication(String heighestQualication) {
        this.heighestQualication = heighestQualication;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    /*     "user_email": " pvvisak123@gmail.com",
             "user_mob": "9656653149",
             "id": "1",
             "user_id": "1",
             "firstname": " visanth",
             "lastname": " pv",
             "user_image": "userImage_15522430564599.jpg",
             "gender": "",
             "dateofbirth": "0000-00-00",
             "state": " 1",
             "city": " Aluva",
             "address": " rterterter",
             "heighestQualication": " post_graduate",
             "mark": "1",
             "institution": " rtertert"*/

}
