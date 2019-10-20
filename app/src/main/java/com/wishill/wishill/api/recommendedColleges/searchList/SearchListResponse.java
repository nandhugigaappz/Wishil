package com.wishill.wishill.api.recommendedColleges.searchList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SearchListResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private List<SearchListData> searchList = new ArrayList<SearchListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SearchListData> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<SearchListData> searchList) {
        this.searchList = searchList;
    }
}
