package com.wishill.wishill.api.recommendedColleges.subCatCollegeList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SubCatCollegeListAPI {
  @FormUrlEncoded
  @POST("listview.php")
  Call<SubCatCollegeResponse> post(
          @Field("wishsubcat_id") String subCatId,
          @Field("countryID") String countryID,
          @Field("stateID") String stateID,
          @Field("cityIDs") String cityIDs,
          @Field("page") String page,
          @Field("courseID") String courseID
  );
}

