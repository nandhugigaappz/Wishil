package com.wishill.wishill.api.recommendedColleges.getScholarshipDetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface ScholarshipDetailsAPI {

  @FormUrlEncoded
  @POST("scholarship_detail.php")
  Call<ScholarshipDetailsResponse> post(
          @Field("scholarshipID") String scholarshipID

  );
}

