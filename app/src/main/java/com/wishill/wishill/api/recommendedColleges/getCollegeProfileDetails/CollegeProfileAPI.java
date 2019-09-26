package com.wishill.wishill.api.recommendedColleges.getCollegeProfileDetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface CollegeProfileAPI {
  @FormUrlEncoded
  @POST("partner_collegeProfile.php")
  Call<CollegeProfileResponse> post(
          @Field("partnerid") String partnerId
  );
}

