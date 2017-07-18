package com.ssa.interfaces;


import com.ssa.models.ParentModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ParentRegistrationService {


    @FormUrlEncoded
    @POST("parentsRegistration/v1/parentRegistration")
    Call<ParentModel> registerParent(@Field("grant_type") String grantType);
}
