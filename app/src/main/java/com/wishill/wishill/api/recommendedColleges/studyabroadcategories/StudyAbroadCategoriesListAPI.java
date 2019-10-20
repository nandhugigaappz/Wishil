package com.wishill.wishill.api.recommendedColleges.studyabroadcategories;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface StudyAbroadCategoriesListAPI {

  @FormUrlEncoded
  @POST("studyabrod_category.php")
  Call<StudyAbroadCategoriesResponse> post(
          @Field("countryID") String countryID
  );
}
