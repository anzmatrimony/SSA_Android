package com.ssa.interfaces;

import com.ssa.models.SchoolsListModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddSchoolPostService {

    @POST("addingSchools/v1/parentSchoolRegistration")
    Call<SchoolsListModel> addSchool(@Body Object object);
}
