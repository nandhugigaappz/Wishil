package com.wishill.wishill.api.recommendedColleges.addPartnerCollege;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by nandhu on 9/26/2016.
 */
public interface AddPartnerCollegeAPI {

  @FormUrlEncoded
  @POST("partner_college_add.php")
  Call<AddPartnerCollegeResponse> postCollege(
          @Field("collegeName") String collegeName,
          @Field("collegeState") String collegeStateId,
          @Field("collegeCity") String collegeCity,
          @Field("userID") String userID,
          @Field("contact_person_name") String contactPersonName,
          @Field("contact_person_contact_number") String contactPersonNumber
  );

  @FormUrlEncoded
  @POST("partner_school_add.php")
  Call<AddPartnerCollegeResponse> postSchool(
          @Field("schoolName") String schoolName,
          @Field("schoolState") String schoolState,
          @Field("schoolCity") String schoolCity,
          @Field("userID") String userID,
          @Field("contact_person_name") String contactPersonName,
          @Field("contact_person_contact_number") String contactPersonNumber
  );

/*  @FormUrlEncoded
  @POST("partner_studytour_add.php")
  Call<AddPartnerCollegeResponse> postStudyTour(
          @Field("studytourID") String studytourId,
          @Field("partner_id") String partnerId,
          @Field("categoryid") String categoryId,
          @Field("pkgname") String packageName,
          @Field("destin") String destination,
          @Field("country") String country,
          @Field("pkgrate") String packageRate,
          @Field("txtnumday") String numberOfDays,
          @Field("txtnumnight") String numberOfNights,
          @Field("valid_start") String startDate,
          @Field("valid_end") String endDate
  );*/
}

