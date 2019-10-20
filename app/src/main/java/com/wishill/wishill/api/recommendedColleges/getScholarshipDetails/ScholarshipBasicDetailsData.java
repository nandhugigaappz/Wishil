package com.wishill.wishill.api.recommendedColleges.getScholarshipDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class ScholarshipBasicDetailsData {
    @SerializedName("title") @Expose private String title;
    @SerializedName("wishsubcat_name") @Expose private String wishsubcat_name;
    @SerializedName("coverImage") @Expose private String coverImage;
    @SerializedName("Description") @Expose private String Description;
    @SerializedName("whoapply") @Expose private String whoapply;
    @SerializedName("award") @Expose private String award;
    @SerializedName("open_on") @Expose private String open_on;
    @SerializedName("closed") @Expose private String closed;
    @SerializedName("tel") @Expose private String tel;
    @SerializedName("email") @Expose private String email;
    @SerializedName("website") @Expose private String website;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOpen_on() {
        return open_on;
    }

    public void setOpen_on(String open_on) {
        this.open_on = open_on;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getWhoapply() {
        return whoapply;
    }

    public void setWhoapply(String whoapply) {
        this.whoapply = whoapply;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getWishsubcat_name() {
        return wishsubcat_name;
    }

    public void setWishsubcat_name(String wishsubcat_name) {
        this.wishsubcat_name = wishsubcat_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
