package com.wishill.wishill.api.recommendedColleges.getCountry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class CountryListData {
    @SerializedName("id") @Expose private String id;
    @SerializedName("country") @Expose private String country;
    @SerializedName("image") @Expose private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
