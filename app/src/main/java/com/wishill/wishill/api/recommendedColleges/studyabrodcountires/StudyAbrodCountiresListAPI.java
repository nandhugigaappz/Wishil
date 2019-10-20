package com.wishill.wishill.api.recommendedColleges.studyabrodcountires;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface StudyAbrodCountiresListAPI {

  @FormUrlEncoded
  @POST("studyabrod_country.php")
  Call<StudyAbrodCountiresResponse> post(
          @Field("value") String value

  );
}

