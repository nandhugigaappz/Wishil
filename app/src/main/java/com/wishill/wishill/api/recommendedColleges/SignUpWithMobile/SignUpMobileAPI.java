package com.wishill.wishill.api.recommendedColleges.SignUpWithMobile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface SignUpMobileAPI {

  @FormUrlEncoded
  @POST("individualSignup.php")
  Call<SignUpMobileResponse> post(
          @Field("userPhone") String mobile
  );

}

