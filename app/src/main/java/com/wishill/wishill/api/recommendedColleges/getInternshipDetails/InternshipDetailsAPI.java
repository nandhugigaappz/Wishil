package com.wishill.wishill.api.recommendedColleges.getInternshipDetails;

import com.wishill.wishill.api.recommendedColleges.internshipList.InternShipResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface InternshipDetailsAPI {

  @FormUrlEncoded
  @POST("intership_detail.php")
  Call<InternshipDetailsResponse> post(
          @Field("intershipID") String id

  );

}

