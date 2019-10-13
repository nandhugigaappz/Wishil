package com.wishill.wishill.api.recommendedColleges.studyTourList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface StudyTourListAPI {

  @FormUrlEncoded
  @POST("studytour_list.php")
  Call<StudyTourResponse> post(
          @Field("categoryid") String catId

  );
}

