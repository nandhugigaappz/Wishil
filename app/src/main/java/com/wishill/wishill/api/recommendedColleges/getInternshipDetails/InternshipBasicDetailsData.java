package com.wishill.wishill.api.recommendedColleges.getInternshipDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class InternshipBasicDetailsData {
    @SerializedName("title") @Expose private String title;
    @SerializedName("companyName") @Expose private String companyName;
    @SerializedName("coverImage") @Expose private String coverImage;
    @SerializedName("skill") @Expose private String skill;
    @SerializedName("whoapply") @Expose private String whoapply;
    @SerializedName("website") @Expose private String website;
    @SerializedName("companyDescription") @Expose private String companyDescription;
    @SerializedName("perks") @Expose private String perks;
    @SerializedName("description") @Expose private String description;
    @SerializedName("stipened") @Expose private String stipened;
    @SerializedName("startDate") @Expose private String startDate;
    @SerializedName("no_avialable") @Expose private String no_avialable;
    @SerializedName("duration") @Expose private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNo_avialable() {
        return no_avialable;
    }

    public void setNo_avialable(String no_avialable) {
        this.no_avialable = no_avialable;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStipened() {
        return stipened;
    }

    public void setStipened(String stipened) {
        this.stipened = stipened;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPerks() {
        return perks;
    }

    public void setPerks(String perks) {
        this.perks = perks;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWhoapply() {
        return whoapply;
    }

    public void setWhoapply(String whoapply) {
        this.whoapply = whoapply;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
