package com.wishill.wishill.api.recommendedColleges.recivedenqlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class RecivedEnqListResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("data") @Expose private List<RecivedEnqListData> dataList = new ArrayList<RecivedEnqListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<RecivedEnqListData> getDataList() {
        return dataList;
    }

    public void setDataList(List<RecivedEnqListData> dataList) {
        this.dataList = dataList;
    }
}
