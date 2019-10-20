package com.wishill.wishill.api.recommendedColleges.internshipList;

import com.wishill.wishill.api.recommendedColleges.addPartnerCollege.AddPartnerCollegeResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface InternShipListAPI {

  @FormUrlEncoded
  @POST("internship_listview.php")
  Call<InternShipResponse> getList(
          @Field("wishsubcat_id") String wishsubcat_id,
          @Field("page") String page
  );
}

