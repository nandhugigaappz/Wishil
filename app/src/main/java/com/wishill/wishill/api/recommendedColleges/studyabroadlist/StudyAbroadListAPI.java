package com.wishill.wishill.api.recommendedColleges.studyabroadlist;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface StudyAbroadListAPI {
  @FormUrlEncoded
  @POST("studyabroadList.php")
  Call<StudyAbroadResponse> post(
          @Field("wishsubcat_id") String subCatId,
          @Field("countryID") String countryID,
          @Field("page") String page,
          @Field("courseID") String courseID
  );
}

