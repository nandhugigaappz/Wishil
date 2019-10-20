package com.wishill.wishill.api.recommendedColleges.getNotifications;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface NotificationListAPI {

  @FormUrlEncoded
  @POST("userNotificationlist.php")
  Call<NotificationResponse> post(
          @Field("userID") String userid

  );
}

