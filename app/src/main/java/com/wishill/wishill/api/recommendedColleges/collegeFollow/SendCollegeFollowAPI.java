package com.wishill.wishill.api.recommendedColleges.collegeFollow;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface SendCollegeFollowAPI {
  @FormUrlEncoded
  @POST("add_to_follow.php")
  Call<SendCollegeFollowResponse> post(
                  @Field("usertypeID") String usertypeID,
                  @Field("itemID") String itemID,
                  @Field("userID") String userID
          );


  @FormUrlEncoded
  @POST("school_follow.php")
  Call<SendCollegeFollowResponse> school(
          @Field("user_name") String userNmae,
          @Field("user_email") String serEmail,
          @Field("mobile") String mobile,
          @Field("schoolID") String schoolID,
          @Field("user_id") String userID

  );

  @FormUrlEncoded
  @POST("studytour_follow.php")
  Call<SendCollegeFollowResponse> tour(
          @Field("user_name") String userNmae,
          @Field("user_email") String serEmail,
          @Field("mobile") String mobile,
          @Field("studytourID") String studytourID,
          @Field("user_id") String userID

  );





}

