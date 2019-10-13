package com.wishill.wishill.api.recommendedColleges.trendingScholerships;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class TrendingScholarshipsResponse {
    @SerializedName("recomm") @Expose private List<TrendingScholarshipsListData> trendingScholarshipsList = new ArrayList<TrendingScholarshipsListData>();

    public List<TrendingScholarshipsListData> getTrendingScholarshipsList() {
        return trendingScholarshipsList;
    }

    public void setTrendingScholarshipsList(List<TrendingScholarshipsListData> trendingScholarshipsList) {
        this.trendingScholarshipsList = trendingScholarshipsList;
    }
}
