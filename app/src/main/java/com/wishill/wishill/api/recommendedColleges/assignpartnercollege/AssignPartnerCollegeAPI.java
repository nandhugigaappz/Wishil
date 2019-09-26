package com.wishill.wishill.api.recommendedColleges.assignpartnercollege;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AssignPartnerCollegeAPI {

  @FormUrlEncoded
  @POST("asignUsertoCollege.php")
  Call<AssignPartnerCollegeResponse> post(
          @Field("collegeID") String collegeID,
          @Field("userID") String userID,
          @Field("userTypeID") String userTypeID
  );
}

