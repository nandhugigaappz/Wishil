package com.wishill.wishill.api.recommendedColleges.getSchoolProfileDetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface SchoolProfileAPI {
  @FormUrlEncoded
  @POST("school_partnerProfile.php")
  Call<SchoolProfileResponse> post(
          @Field("partnerid") String partnerId
  );
}

