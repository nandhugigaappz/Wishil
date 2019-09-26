package com.wishill.wishill.api.recommendedColleges.toprankingColleges;

import com.wishill.wishill.api.recommendedColleges.collegeCourseList.CourseListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface TopRankingCollegesListAPI {

/*  @GET("attuniversity.php")
  Call<TopRankingCollegesResponse> getList(
          @Query("keyword") String keyword,
          @Query("userID") String userID
  );*/
  @FormUrlEncoded
  @POST("attuniversity.php")
  Call<TopRankingCollegesResponse> getList(
          @Field("userID") String userID
  );
}

