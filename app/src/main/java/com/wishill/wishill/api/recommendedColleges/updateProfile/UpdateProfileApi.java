package com.wishill.wishill.api.recommendedColleges.updateProfile;

/*
 * Created by nithinp on 20-02-2018.
 */

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UpdateProfileApi {
    @Multipart
    @POST("edit_userProfile.php")
    Call<UpdateProfileResponse> post(
            @Part("userID") RequestBody userID,
            @Part("userfirstName") RequestBody userfirstName,
            @Part("userlastName") RequestBody userlastName,
            @Part("userEmail") RequestBody userEmail,
            @Part("userPhone") RequestBody userPhone,
            @Part("userGender") RequestBody userGender,
            @Part("userState") RequestBody userState,
            @Part("userCity") RequestBody userCity,
            @Part("userAdddess") RequestBody userAdddess,
            @Part("Qualification") RequestBody Qualification,
            @Part("mark") RequestBody mark,
            @Part("institution") RequestBody institution,
            @Part("dob") RequestBody dob,
            @Part MultipartBody.Part userProfilePicture
    );


}
