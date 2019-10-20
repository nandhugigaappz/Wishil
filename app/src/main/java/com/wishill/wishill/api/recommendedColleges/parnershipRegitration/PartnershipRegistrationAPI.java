package com.wishill.wishill.api.recommendedColleges.parnershipRegitration;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface PartnershipRegistrationAPI {

  @FormUrlEncoded
  @POST("partner_wishill.php")
  Call<PartnershipRegistrationResponse> post(
          @Field("partnerType") String partnerType,
          @Field("partnerName") String partnerName,
          @Field("partnerEmail") String partnerEmail,
          @Field("password") String password,
          @Field("mobilenum") String mobilenum
  );
}

