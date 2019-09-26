package com.wishill.wishill.api.recommendedColleges.getNotifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class NotificationResponse {

    @SerializedName("success") @Expose private String status;
    @SerializedName("data") @Expose private List<NotificationListData> catList = new ArrayList<NotificationListData>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NotificationListData> getCatList() {
        return catList;
    }

    public void setCatList(List<NotificationListData> catList) {
        this.catList = catList;
    }
}
