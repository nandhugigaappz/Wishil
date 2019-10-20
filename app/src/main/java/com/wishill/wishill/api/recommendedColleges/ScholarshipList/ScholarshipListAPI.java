package com.wishill.wishill.api.recommendedColleges.ScholarshipList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface ScholarshipListAPI {

  @FormUrlEncoded
  @POST("scholarship_list.php")
  Call<ScholarshipResponse> post(
          @Field("wishsubcat_id") String wishSubCatId,
          @Field("page") String page
  );
}

