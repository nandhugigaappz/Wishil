package com.wishill.wishill.api.recommendedColleges.attractiveStudyTour;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface AttractiveStudyTourListAPI {

  @GET("attractivetour.php")
  Call<AttractiveStudyTourResponse> getList(
          @Query("keyword") String keyword);
}

