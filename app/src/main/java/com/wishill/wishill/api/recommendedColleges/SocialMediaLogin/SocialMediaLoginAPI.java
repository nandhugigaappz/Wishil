package com.wishill.wishill.api.recommendedColleges.SocialMediaLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SocialMediaLoginAPI {

  @FormUrlEncoded
  @POST("socialLogin.php")
  Call<SocialMediaLoginResponse> post(
          @Field("appID") String appID,
          @Field("userLoginType") String userLoginType,
          @Field("userEmail") String userEmail,
          @Field("userName") String userName,
          @Field("picture") String picture,
          @Field("referrer") String referrer,
          @Field("referredInstitute") String referredCollege,
          @Field("referredInstituteType") String referredInstituteType
  );
}

