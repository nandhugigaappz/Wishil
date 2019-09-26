package com.wishill.wishill.api.recommendedColleges.studyabroaddetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface StudyAbroadDetailsAPI {

  @FormUrlEncoded
  @POST("studyabroad_detail.php")
  Call<StudyAbroadDetailsResponse> post(
          @Field("userID") String userID,
          @Field("studyabroadID") String studyabroadID

  );
}

