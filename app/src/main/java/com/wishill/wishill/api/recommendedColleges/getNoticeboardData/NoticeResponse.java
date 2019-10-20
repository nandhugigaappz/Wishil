package com.wishill.wishill.api.recommendedColleges.getNoticeboardData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NoticeResponse {
    @SerializedName("success") @Expose private String status;
    @SerializedName("ImagePath") @Expose private String ImagePath;
    @SerializedName("recom") @Expose private List<NoticeData> catList = new ArrayList<NoticeData>();

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

    public List<NoticeData> getCatList() {
        return catList;
    }

    public void setCatList(List<NoticeData> catList) {
        this.catList = catList;
    }
}
