package com.wishill.wishill.api.recommendedColleges.sendenqlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SendEnqListResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private List<SendEnqListData> dataList = new ArrayList<SendEnqListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SendEnqListData> getDataList() {
        return dataList;
    }

    public void setDataList(List<SendEnqListData> dataList) {
        this.dataList = dataList;
    }
}
