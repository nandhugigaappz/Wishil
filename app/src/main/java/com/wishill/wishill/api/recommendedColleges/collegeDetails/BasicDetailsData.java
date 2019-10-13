package com.wishill.wishill.api.recommendedColleges.collegeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class BasicDetailsData {
    @SerializedName("name") @Expose private String name;
    @SerializedName("city") @Expose private String city;
    @SerializedName("campus_details") @Expose private String campus_details;
    @SerializedName("established_year") @Expose private String established_year;
    @SerializedName("affiliate_detail_university") @Expose private String affiliate_detail_university;
    @SerializedName("phone") @Expose private String phone;
    @SerializedName("email") @Expose private String email;
    @SerializedName("website") @Expose private String website;
    @SerializedName("coverImage") @Expose private String coverImage;
    @SerializedName("logo") @Expose private String logo;
    @SerializedName("map_lat") @Expose private String mapLat;
    @SerializedName("map_lon") @Expose private String mapLon;

    public String getMapLat() {
        return mapLat;
    }

    public void setMapLat(String mapLat) {
        this.mapLat = mapLat;
    }

    public String getMapLon() {
        return mapLon;
    }

    public void setMapLon(String mapLon) {
        this.mapLon = mapLon;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAffiliate_detail_university() {
        return affiliate_detail_university;
    }

    public void setAffiliate_detail_university(String affiliate_detail_university) {
        this.affiliate_detail_university = affiliate_detail_university;
    }

    public String getEstablished_year() {
        return established_year;
    }

    public void setEstablished_year(String established_year) {
        this.established_year = established_year;
    }

    public String getCampus_details() {
        return campus_details;
    }

    public void setCampus_details(String campus_details) {
        this.campus_details = campus_details;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
