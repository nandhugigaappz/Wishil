package com.wishill.wishill.api.recommendedColleges.getNoticeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeDetails {
    @SerializedName("noticeImage") @Expose private String noticeImage;
    @SerializedName("headline") @Expose private String noticeTitle;
    @SerializedName("created_date_time") @Expose private String dateOfIssue;
    @SerializedName("content") @Expose private String noticeContent;

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

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
}
