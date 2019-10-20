package com.wishill.wishill.api.recommendedColleges.partnerChangePassword;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface PartnerChangePasswordAPI {

  @FormUrlEncoded
  @POST("partner_changepassword.php")
  Call<PartnerChangePasswordResponse> post(
          @Field("partnerid") String userId,
          @Field("newpassword") String password
  );

  @FormUrlEncoded
  @POST("user_changepassword.php")
  Call<PartnerChangePasswordResponse> normalUser(
          @Field("userid") String userId,
          @Field("newpassword") String password
  );

}

