package com.wishill.wishill.api.recommendedColleges.getStudyTourCat;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface StudyTourCatListAPI {

  @FormUrlEncoded
  @POST("studytour_catlist.php")
  Call<StudyTourCatListResponse> post(
          @Field("value") String value

  );
}

