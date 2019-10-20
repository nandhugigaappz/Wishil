package com.wishill.wishill.api.recommendedColleges.userfollowing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class UserFollowingResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("data") @Expose private List<UserFollowingData> dataList = new ArrayList<UserFollowingData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<UserFollowingData> getDataList() {
        return dataList;
    }

    public void setDataList(List<UserFollowingData> dataList) {
        this.dataList = dataList;
    }
}
