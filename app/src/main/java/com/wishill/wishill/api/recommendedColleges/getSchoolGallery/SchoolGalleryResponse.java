package com.wishill.wishill.api.recommendedColleges.getSchoolGallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SchoolGalleryResponse {
    @SerializedName("success") @Expose private String status;
    @SerializedName("ImagePath") @Expose private String ImagePath;
    @SerializedName("recom") @Expose private List<SchoolGalleryListData> catList = new ArrayList<SchoolGalleryListData>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public List<SchoolGalleryListData> getCatList() {
        return catList;
    }

    public void setCatList(List<SchoolGalleryListData> catList) {
        this.catList = catList;
    }
}
