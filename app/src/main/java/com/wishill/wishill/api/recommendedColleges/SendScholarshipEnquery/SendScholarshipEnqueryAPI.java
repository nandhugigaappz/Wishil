package com.wishill.wishill.api.recommendedColleges.SendScholarshipEnquery;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface SendScholarshipEnqueryAPI {

  @FormUrlEncoded
  @POST("en_studytour.php")
  Call<SendScholarshipEnqueryResponse> post(
          @Field("user_name") String userNmae,
          @Field("user_email") String serEmail,
          @Field("mobile") String mobile,
          @Field("user_id") String userId,
          @Field("scholarid") String scholarid

  );
}

