package com.wishill.wishill.api.recommendedColleges.internshipList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class InternShipResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private List<InternShipData> catList = new ArrayList<InternShipData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<InternShipData> getCatList() {
        return catList;
    }

    public void setCatList(List<InternShipData> catList) {
        this.catList = catList;
    }
}
