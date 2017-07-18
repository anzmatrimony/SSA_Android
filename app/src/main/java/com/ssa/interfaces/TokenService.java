package com.ssa.interfaces;

import com.ssa.models.TokenModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TokenService {

    @FormUrlEncoded
    @POST("token")
    Call<TokenModel> getToken(@Field("grant_type") String grantType);
}
