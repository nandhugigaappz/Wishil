package com.wishill.wishill.api.recommendedColleges.partnerEnquiriesList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class partnerEnquiriesListResponse {
    @SerializedName("recomm") @Expose private List<partnerEnquiriesListData> detailsList = new ArrayList<partnerEnquiriesListData>();
    @SerializedName("success") @Expose private Integer success;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<partnerEnquiriesListData> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<partnerEnquiriesListData> detailsList) {
        this.detailsList = detailsList;
    }
}
