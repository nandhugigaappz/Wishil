package com.wishill.wishill.api.recommendedColleges.subCatSchoolList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface SubCatSchoolListAPI {
  @FormUrlEncoded
  @POST("school_list.php")
  Call<SubCatSchoolResponse> post(
          @Field("wishsubcat_id") String subCatId,
          @Field("countryID") String countryID,
          @Field("stateID") String stateID,
          @Field("cityIDs") String cityIDs,
          @Field("page") String page,
          @Field("courseID") String courseID

  );
}

