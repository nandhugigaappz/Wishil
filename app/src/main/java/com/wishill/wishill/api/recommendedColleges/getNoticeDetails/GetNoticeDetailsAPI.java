package com.wishill.wishill.api.recommendedColleges.getNoticeDetails;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetNoticeDetailsAPI {
    @FormUrlEncoded
    @POST("notice_details.php")
    Call<NoticeDetailsResponse> post(
            @Field("noticeID") String noticeId,
            @Field("instituteType") String instituteType
    );
}
