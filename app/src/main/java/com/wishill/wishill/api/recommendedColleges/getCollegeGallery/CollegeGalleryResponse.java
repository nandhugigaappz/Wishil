package com.wishill.wishill.api.recommendedColleges.getCollegeGallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class CollegeGalleryResponse {
    @SerializedName("success") @Expose private String status;
    @SerializedName("ImagePath") @Expose private String ImagePath;
    @SerializedName("recom") @Expose private List<CollegeGalleryListData> catList = new ArrayList<CollegeGalleryListData>();

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

    public List<CollegeGalleryListData> getCatList() {
        return catList;
    }

    public void setCatList(List<CollegeGalleryListData> catList) {
        this.catList = catList;
    }
}
