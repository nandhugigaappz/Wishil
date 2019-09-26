package com.wishill.wishill.api.recommendedColleges.SignUpWithMobileOTP;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface SignUpOTPVerificationAPI {

  @FormUrlEncoded
  @POST("otp_verification.php")
  Call<SignUpOTPVerificationResponse> post(
          @Field("otp") String otp,
          @Field("userID") String userID
  );
}

