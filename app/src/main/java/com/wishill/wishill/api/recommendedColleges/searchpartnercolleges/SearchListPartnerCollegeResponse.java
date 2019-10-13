package com.wishill.wishill.api.recommendedColleges.searchpartnercolleges;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class SearchListPartnerCollegeResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private List<SearchListPartnerCollegeData> searchList = new ArrayList<SearchListPartnerCollegeData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SearchListPartnerCollegeData> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<SearchListPartnerCollegeData> searchList) {
        this.searchList = searchList;
    }
}
