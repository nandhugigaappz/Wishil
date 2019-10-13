package com.wishill.wishill.api.recommendedColleges.getProfile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface ProfileAPI {
  @FormUrlEncoded
  @POST("get_userProfile.php")
  Call<ProfileResponse> post(
          @Field("userID") String userID,
          @Field("usertypeID") String usertypeID
  );
}

