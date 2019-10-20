package com.wishill.wishill.api.recommendedColleges.termsAndCondition;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class TermsAndConditionResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recomm") @Expose private List<TermsAndConditionData> list = new ArrayList<TermsAndConditionData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TermsAndConditionData> getList() {
        return list;
    }

    public void setList(List<TermsAndConditionData> list) {
        this.list = list;
    }
}
