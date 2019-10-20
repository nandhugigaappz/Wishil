package com.wishill.wishill.api.recommendedColleges.SchoolFess;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface SchoolFeesListAPI {

  @FormUrlEncoded
  @POST("school_fees.php")
  Call<SchoolFeesResponse> post(
          @Field("schoolID") String schoolID

  );
}

