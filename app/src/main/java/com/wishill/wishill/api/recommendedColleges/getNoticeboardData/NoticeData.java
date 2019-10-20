package com.wishill.wishill.api.recommendedColleges.getNoticeboardData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeData {
    @SerializedName("notificationID") @Expose private String noticeId;
    @SerializedName("noticeImage") @Expose private String noticeImage;
    @SerializedName("headline") @Expose private String noticeTitle;
    @SerializedName("created_date_time") @Expose private String dateOfIssue;

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeImage() {
        return noticeImage;
    }

    public void setNoticeImage(String noticeImage) {
        this.noticeImage = noticeImage;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }
}
