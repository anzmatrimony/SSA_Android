package com.ssa.interfaces;

import com.ssa.models.SchoolModel;
import com.ssa.models.SchoolsListModel;
import com.ssa.models.StatusModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface AddSchoolGetService {

    @GET
    Call<SchoolsListModel> addSchool(@Url String url);
}
