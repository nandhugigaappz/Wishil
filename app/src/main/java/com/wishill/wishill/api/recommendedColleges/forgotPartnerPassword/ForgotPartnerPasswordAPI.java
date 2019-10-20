package com.wishill.wishill.api.recommendedColleges.forgotPartnerPassword;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface ForgotPartnerPasswordAPI {

  @FormUrlEncoded
  @POST("partner_forgot.php")
  Call<ForgotPartnerPasswordResponse> post(
          @Field("email") String email
  );

  @FormUrlEncoded
  @POST("user_forgot.php")
  Call<ForgotPartnerPasswordResponse> normalUser(
          @Field("email") String email
  );


}

