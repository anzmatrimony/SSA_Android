package com.ssa.interfaces;

import com.ssa.models.RegisterModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RegisterService {

    @POST("parentsRegistration/v1/parentRegistration")
    Call<RegisterModel> registerUser(@Body Object object);
}
