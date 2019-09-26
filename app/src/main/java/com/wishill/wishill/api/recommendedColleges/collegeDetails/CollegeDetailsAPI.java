package com.wishill.wishill.api.recommendedColleges.collegeDetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface CollegeDetailsAPI {

  @FormUrlEncoded
  @POST("details_view.php")
  Call<CollegeDetailsResponse> post(
          @Field("collegeID") String subCatId,
          @Field("userID") String userID

  );
}

