package com.wishill.wishill.api.recommendedColleges.blogdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class BlogDetailsResponse {
    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private BlogDetailsData enqDetailsData;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BlogDetailsData getEnqDetailsData() {
        return enqDetailsData;
    }

    public void setEnqDetailsData(BlogDetailsData enqDetailsData) {
        this.enqDetailsData = enqDetailsData;
    }
}
