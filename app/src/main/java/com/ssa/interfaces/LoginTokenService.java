package com.ssa.interfaces;

import com.ssa.models.TokenModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginTokenService {

    @FormUrlEncoded
    @POST("token")
    Call<TokenModel> getLoginToken(@Field("grant_type") String grantType,@Field("password") String password,@Field("username") String username);
}
