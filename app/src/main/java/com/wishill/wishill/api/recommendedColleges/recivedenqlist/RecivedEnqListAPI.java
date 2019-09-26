package com.wishill.wishill.api.recommendedColleges.recivedenqlist;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface RecivedEnqListAPI {

  @FormUrlEncoded
  @POST("myReceivedEnquiry.php")
  Call<RecivedEnqListResponse> post(
          @Field("userID") String userID
  );
}

