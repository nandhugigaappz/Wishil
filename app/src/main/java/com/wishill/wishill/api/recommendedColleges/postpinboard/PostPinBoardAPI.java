package com.wishill.wishill.api.recommendedColleges.postpinboard;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface PostPinBoardAPI {

  @FormUrlEncoded
  @POST("add_notification.php")
  Call<PostPinBoardResponse> post(
          @Field("usertypeID") String usertypeID,
          @Field("itemID") String itemID,
          @Field("content") String content
  );

}

