package com.ssa.interfaces;

import com.ssa.models.ActivityResModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ActivitiesListService {

    @GET
    Call<ActivityResModel> getActivities(@Url String url);
}
