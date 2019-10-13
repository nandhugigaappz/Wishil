package com.wishill.wishill.api.recommendedColleges.regitration;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface RegistrationAPI {

  @FormUrlEncoded
  @POST("usersignup.php")
  Call<RegistrationResponse> post(
          @Field("firstname") String firstName,
          @Field("lastname") String lastName,
          @Field("email") String email,
          @Field("gender") String gender,
          @Field("mobile") String mobile,
          @Field("password") String password,
          @Field("postdate") String postdate

  );
}

