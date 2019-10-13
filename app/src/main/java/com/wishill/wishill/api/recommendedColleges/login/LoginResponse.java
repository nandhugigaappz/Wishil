package com.wishill.wishill.api.recommendedColleges.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class LoginResponse {
    @SerializedName("success") @Expose private Integer success;
    @SerializedName("message") @Expose private String message;
    @SerializedName("recomm") @Expose private List<LoginDetailsData> detailsList = new ArrayList<LoginDetailsData>();

    public List<LoginDetailsData> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<LoginDetailsData> detailsList) {
        this.detailsList = detailsList;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
