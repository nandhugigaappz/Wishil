package com.wishill.wishill.api.recommendedColleges.getState;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class StateListResponse {
    @SerializedName("recom") @Expose private List<StateListData> catList = new ArrayList<StateListData>();

    public List<StateListData> getCatList() {
        return catList;
    }

    public void setCatList(List<StateListData> catList) {
        this.catList = catList;
    }
}
