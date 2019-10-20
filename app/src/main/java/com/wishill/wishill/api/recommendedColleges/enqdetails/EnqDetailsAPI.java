package com.wishill.wishill.api.recommendedColleges.enqdetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface EnqDetailsAPI {

  @FormUrlEncoded
  @POST("userEnquiryview.php")
  Call<EnqDetailsResponse> post(
          @Field("enquiryID") String enquiryID
  );
}

