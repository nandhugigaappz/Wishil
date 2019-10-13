package com.wishill.wishill.api.recommendedColleges.sendNotification;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface SendNotificationAPI {

  @FormUrlEncoded
  @POST("partner_sendnotif.php")
  Call<SendNotificationResponse> post(
          @Field("partnerid") String partnerID,
          @Field("header") String subject,
          @Field("content") String comment,
          @Field("usertype") String userType,
          @Field("insti_id") String institutionID
  );







}

