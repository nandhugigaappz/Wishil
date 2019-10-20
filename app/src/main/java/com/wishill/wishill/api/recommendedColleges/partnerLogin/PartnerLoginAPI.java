package com.wishill.wishill.api.recommendedColleges.partnerLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface PartnerLoginAPI {
  @FormUrlEncoded
  @POST("partner_login.php")
  Call<PartnerResponse> post(
          @Field("emilID") String email,
          @Field("password") String password,
          @Field("referrer") String referrer,
          @Field("referredInstitute") String referredCollege,
          @Field("referredInstituteType") String referredInstituteType
  );
}