package com.wishill.wishill.api.recommendedColleges.enqdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class EnqDetailsResponse {
    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private EnqDetailsData enqDetailsData;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public EnqDetailsData getEnqDetailsData() {
        return enqDetailsData;
    }

    public void setEnqDetailsData(EnqDetailsData enqDetailsData) {
        this.enqDetailsData = enqDetailsData;
    }
}
