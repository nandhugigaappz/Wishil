package com.wishill.wishill.api.recommendedColleges.examList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface ExamListAPI {

  @FormUrlEncoded
  @POST("examlist.php")
  Call<ExamResponse> post(
          @Field("examid") String examID

  );
}

