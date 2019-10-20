package com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface SendCollegeEnqueryAPI {

  @FormUrlEncoded
  @POST("enquiry.php")
  Call<SendCollegeEnqueryResponse> post(
          @Field("contactName") String contactName,
          @Field("userID") String userID,
          @Field("contactEmail") String contactEmail,
          @Field("contactPhone") String contactPhone,
          @Field("enquiryType") String enquiryType,
          @Field("itemId") String itemId,
          @Field("courseID") String courseID,
          @Field("contactContent") String contactContent
  );

}

