package com.wishill.wishill.api.recommendedColleges.userfollowing;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface UserFollowingListAPI {

  @FormUrlEncoded
  @POST("userFollowlist.php")
  Call<UserFollowingResponse> post(
          @Field("userID") String userID

  );
}

