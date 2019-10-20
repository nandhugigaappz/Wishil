package com.wishill.wishill.api.recommendedColleges.mywishlist;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface MyWishListAPI {

  @FormUrlEncoded
  @POST("userWishlist.php")
  Call<MyWishListResponse> post(
          @Field("userID") String userID

  );
}

