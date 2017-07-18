package com.ssa.interfaces;

import com.ssa.models.KidModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface KidsListService {

    @GET
    Call<KidModel> getSchools(@Url String url);
}
