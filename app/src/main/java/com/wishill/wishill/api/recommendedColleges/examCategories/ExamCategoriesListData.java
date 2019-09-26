package com.wishill.wishill.api.recommendedColleges.examCategories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class ExamCategoriesListData {
    @SerializedName("exam_id") @Expose private String examID;
    @SerializedName("exam_name") @Expose private String examName;
    @SerializedName("exam_img") @Expose private String examImage;

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamImage() {
        return examImage;
    }

    public void setExamImage(String examImage) {
        this.examImage = examImage;
    }
}
