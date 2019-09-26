package com.wishill.wishill.api.recommendedColleges.getState;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface StateListAPI {

  @FormUrlEncoded
  @POST("statelist.php")
  Call<StateListResponse> post(
          @Field("country") String country

  );
}