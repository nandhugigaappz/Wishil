package com.wishill.wishill.api.recommendedColleges.storieslist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class StoriesListResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private List<StoriesListData> dataList = new ArrayList<StoriesListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StoriesListData> getDataList() {
        return dataList;
    }

    public void setDataList(List<StoriesListData> dataList) {
        this.dataList = dataList;
    }
}
