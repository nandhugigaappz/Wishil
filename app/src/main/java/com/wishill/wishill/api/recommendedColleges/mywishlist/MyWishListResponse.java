package com.wishill.wishill.api.recommendedColleges.mywishlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class MyWishListResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("data") @Expose private List<MyWishListData> dataList = new ArrayList<MyWishListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<MyWishListData> getDataList() {
        return dataList;
    }

    public void setDataList(List<MyWishListData> dataList) {
        this.dataList = dataList;
    }
}
