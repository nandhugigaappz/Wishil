package com.wishill.wishill.api.recommendedColleges.myfollowerslist;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface MyFollowersListAPI {

  @FormUrlEncoded
  @POST("recievedFollowList.php")
  Call<MyFollowersListResponse> post(
          @Field("usertypeID") String usertypeID,
          @Field("itemID") String itemID

  );
}

