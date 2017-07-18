package com.ssa.interfaces;

import com.ssa.models.LoginModel;
import com.ssa.models.RegisterModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface LoginService {

    @GET
    Call<LoginModel> login(@Url String url);
}
