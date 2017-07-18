package com.ssa.interfaces;

import com.ssa.models.StatusModel;
import com.ssa.models.TokenModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface CheckEmailService {

    @GET
    Call<StatusModel> checkEmail(@Url  String url);
}
