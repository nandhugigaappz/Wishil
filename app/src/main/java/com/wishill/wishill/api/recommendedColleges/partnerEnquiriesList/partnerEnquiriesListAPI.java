package com.wishill.wishill.api.recommendedColleges.partnerEnquiriesList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface partnerEnquiriesListAPI {
  @FormUrlEncoded
  @POST("college_enquirylist.php")
  Call<partnerEnquiriesListResponse> college(
          @Field("en_college") String partnerId
  );


  @FormUrlEncoded
  @POST("studytour_enquiry.php")
  Call<partnerEnquiriesListResponse> studyTour(
          @Field("studytourid") String partnerId
  );

  @FormUrlEncoded
  @POST("school_enquiry.php")
  Call<partnerEnquiriesListResponse> school(
          @Field("en_school") String partnerId
  );
}

