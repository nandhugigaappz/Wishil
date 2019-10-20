package com.wishill.wishill.api.recommendedColleges.getNoticeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NoticeDetailsResponse {
    @SerializedName("success") @Expose private String status;
    @SerializedName("ImagePath") @Expose private String ImagePath;
    @SerializedName("recom") @Expose private List<NoticeDetails> catList = new ArrayList<NoticeDetails>();

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

    public List<NoticeDetails> getCatList() {
        return catList;
    }

    public void setCatList(List<NoticeDetails> catList) {
        this.catList = catList;
    }
}
