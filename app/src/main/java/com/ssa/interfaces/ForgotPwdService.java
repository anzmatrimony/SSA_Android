package com.ssa.interfaces;

import com.ssa.models.ForgotPwdResModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ForgotPwdService {

    @POST("forgetpasword/1.0/forgotPassword")
    Call<ForgotPwdResModel> forgotPwd(@Body Object object);
}
