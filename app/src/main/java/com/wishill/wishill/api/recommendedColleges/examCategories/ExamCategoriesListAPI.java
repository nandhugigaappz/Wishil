package com.wishill.wishill.api.recommendedColleges.examCategories;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface ExamCategoriesListAPI {

  @FormUrlEncoded
  @POST("examp_category.php")
  Call<ExamCategoriesResponse> post(
          @Field("wishctgry_id") String catgoryID

  );
}

