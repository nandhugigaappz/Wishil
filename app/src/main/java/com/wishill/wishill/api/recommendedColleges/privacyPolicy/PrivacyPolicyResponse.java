package com.wishill.wishill.api.recommendedColleges.privacyPolicy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class PrivacyPolicyResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recomm") @Expose private List<PrivacyPolicyData> list = new ArrayList<PrivacyPolicyData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PrivacyPolicyData> getList() {
        return list;
    }

    public void setList(List<PrivacyPolicyData> list) {
        this.list = list;
    }
}
