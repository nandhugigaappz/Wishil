package com.wishill.wishill.api.recommendedColleges.collegeWishList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface SendCollegeWishListAPI {

  @FormUrlEncoded
  @POST("add_to_wishlist.php")
  Call<SendCollegeWishListResponse> post(
          @Field("usertypeID") String userTypeId,
          @Field("itemID") String itemID,
          @Field("userID") String userID
  );

  @FormUrlEncoded
  @POST("school_wishlist.php")
  Call<SendCollegeWishListResponse> school(
          @Field("user_name") String userNmae,
          @Field("user_email") String serEmail,
          @Field("mobile") String mobile,
          @Field("schoolID") String schoolID,
          @Field("user_id") String userId
  );

    @FormUrlEncoded
    @POST("styudytour_wishlist.php")
    Call<SendCollegeWishListResponse> tour(
            @Field("user_name") String userNmae,
            @Field("user_email") String serEmail,
            @Field("mobile") String mobile,
            @Field("packageID") String packageID,
            @Field("user_id") String userId
    );

}

