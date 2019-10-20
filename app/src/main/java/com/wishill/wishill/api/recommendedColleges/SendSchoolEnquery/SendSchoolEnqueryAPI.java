package com.wishill.wishill.api.recommendedColleges.SendSchoolEnquery;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface SendSchoolEnqueryAPI {

  @FormUrlEncoded
  @POST("en_school.php")
  Call<SendSchoolEnqueryResponse> post(
          @Field("user_name") String userNmae,
          @Field("user_email") String serEmail,
          @Field("mobile") String mobile,
          @Field("user_id") String userId,
          @Field("schoolID") String schoolID,
          @Field("msg") String msg

  );

  @FormUrlEncoded
  @POST("en_studytour.php")
  Call<SendSchoolEnqueryResponse> studyTour(
          @Field("user_name") String userNmae,
          @Field("user_email") String serEmail,
          @Field("mobile") String mobile,
          @Field("user_id") String userId,
          @Field("sudytourid") String studyTourId,
          @Field("msg") String msg

  );
}

