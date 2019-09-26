package com.wishill.wishill.api.recommendedColleges.SignUpWithMobileOTP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class SignUpOTPVerificationResponse {
   /* @SerializedName("success") @Expose private Integer success;
    @SerializedName("message") @Expose private String message;
    @SerializedName("recomm") @Expose private List<SignUpOTPVerificationData> dataList = new ArrayList<SignUpOTPVerificationData>();*/

    @SerializedName("success") @Expose private String success;
    @SerializedName("message") @Expose private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
