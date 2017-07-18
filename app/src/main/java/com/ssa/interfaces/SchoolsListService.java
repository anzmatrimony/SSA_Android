package com.ssa.interfaces;

import com.ssa.models.SchoolModel;
import com.ssa.models.StatusModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface SchoolsListService {

    @GET
    Call<SchoolModel> getSchools(@Url String url);
}
