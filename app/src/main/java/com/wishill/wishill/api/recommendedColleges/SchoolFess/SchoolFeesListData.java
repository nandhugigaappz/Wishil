package com.wishill.wishill.api.recommendedColleges.SchoolFess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class SchoolFeesListData {
    @SerializedName("fee_id") @Expose private String feesId;
    @SerializedName("groupp") @Expose private String group;
    @SerializedName("fees_term1") @Expose private String termOne;
    @SerializedName("fees_term2") @Expose private String termTwo;
    @SerializedName("fees_term3") @Expose private String termThree;
    @SerializedName("fees_term4") @Expose private String termFour;
    @SerializedName("e_year") @Expose private String totalFees;

    public String getFeesId() {
        return feesId;
    }

    public void setFeesId(String feesId) {
        this.feesId = feesId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTermOne() {
        return termOne;
    }

    public void setTermOne(String termOne) {
        this.termOne = termOne;
    }

    public String getTermTwo() {
        return termTwo;
    }

    public void setTermTwo(String termTwo) {
        this.termTwo = termTwo;
    }

    public String getTermThree() {
        return termThree;
    }

    public void setTermThree(String termThree) {
        this.termThree = termThree;
    }

    public String getTermFour() {
        return termFour;
    }

    public void setTermFour(String termFour) {
        this.termFour = termFour;
    }

    public String getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(String totalFees) {
        this.totalFees = totalFees;
    }
}
