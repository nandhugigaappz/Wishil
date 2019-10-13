package com.wishill.wishill.api.recommendedColleges.attractiveStudyTour;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class AttractiveStudyTourListData {
    @SerializedName("logoimage") @Expose private String logoimage;
    @SerializedName("packid") @Expose private String packid;
    @SerializedName("packagename") @Expose private String packagename;
    @SerializedName("location") @Expose private String location;

    public String getLogoimage() {
        return logoimage;
    }

    public void setLogoimage(String logoimage) {
        this.logoimage = logoimage;
    }

    public String getPackid() {
        return packid;
    }

    public void setPackid(String packid) {
        this.packid = packid;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
