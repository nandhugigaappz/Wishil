package com.wishill.wishill.api.recommendedColleges.getStudyTourProfile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface StudyTourProfileAPI {
  @FormUrlEncoded
  @POST("studytour_Profile.php")
  Call<StudyTourProfileResponse> post(
          @Field("partnerid") String partnerId
  );
}

