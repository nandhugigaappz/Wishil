package com.wishill.wishill.api.recommendedColleges.myfollowerslist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class MyFollowersListResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("profileUrl") @Expose private String profileUrl;
    @SerializedName("data") @Expose private List<MyFollowersListData> dataList = new ArrayList<MyFollowersListData>();

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<MyFollowersListData> getDataList() {
        return dataList;
    }

    public void setDataList(List<MyFollowersListData> dataList) {
        this.dataList = dataList;
    }
}
