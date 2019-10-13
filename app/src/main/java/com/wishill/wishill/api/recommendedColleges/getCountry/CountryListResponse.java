package com.wishill.wishill.api.recommendedColleges.getCountry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class CountryListResponse {
    @SerializedName("recom") @Expose private List<CountryListData> catList = new ArrayList<CountryListData>();

    public List<CountryListData> getCatList() {
        return catList;
    }

    public void setCatList(List<CountryListData> catList) {
        this.catList = catList;
    }
}
