package com.wishill.wishill.api.recommendedColleges.termsAndCondition;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class TermsAndConditionData {
    @SerializedName("head") @Expose private String head;
    @SerializedName("terms") @Expose private String terms;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }
}
