package com.wishill.wishill.api.recommendedColleges.otpVerification;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface OTPVerificationAPI {

  @FormUrlEncoded
  @POST("otp_verification.php")
  Call<OTPVerificationResponse> post(
          @Field("mobilenum") String mobileNumber,
          @Field("otp") String otp
  );

}

