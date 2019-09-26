package com.wishill.wishill.api.recommendedColleges.schoolDetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface SchoolDetailsAPI {

  @FormUrlEncoded
  @POST("school.php")
  Call<SchoolDetailsResponse> post(
          @Field("schoolID") String schoolID,
          @Field("userID") String userID

  );
}

