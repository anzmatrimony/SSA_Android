package com.ssa.interfaces;


import com.ssa.models.KidModel;
import com.ssa.models.KidResModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddKidService {

    @POST("addKids/v1/kidRegistration")
    Call<KidResModel> addKid(@Body Object object);
}
