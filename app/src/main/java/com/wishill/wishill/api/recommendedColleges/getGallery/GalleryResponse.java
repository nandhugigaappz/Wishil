package com.wishill.wishill.api.recommendedColleges.getGallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GalleryResponse {
    @SerializedName("success") @Expose private String status;
    @SerializedName("ImagePath") @Expose private String ImagePath;
    @SerializedName("recom") @Expose private List<GalleryListData> catList = new ArrayList<GalleryListData>();


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

    public List<GalleryListData> getCatList() {
        return catList;
    }

    public void setCatList(List<GalleryListData> catList) {
        this.catList = catList;
    }
}
