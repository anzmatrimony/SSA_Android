package com.ssa.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssa.LoginActivity;
import com.ssa.R;
import com.ssa.WebViewActivity;
import com.ssa.adapters.ActivitiesAdapter;
import com.ssa.interfaces.ActivitiesListService;
import com.ssa.models.ActivityResModel;
import com.ssa.utils.ConstantValues;

import java.io.IOException;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivitiesFragment extends Fragment implements ActivitiesAdapter.OnItemClickListener {

    private SharedPreferences mSharedPreferences;
    private String mLatitude = "0.0", mLongitude = "0.0";
    private ActivitiesAdapter activitiesAdapter;
    private RecyclerView rvKidActivity;
    private String userRef, token;
    private TextView tvNoActivities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activities, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        token = mSharedPreferences.getString(ConstantValues.TOKEN, "");

        rvKidActivity = (RecyclerView) view.findViewById(R.id.rv_kid_activity);
        tvNoActivities = (TextView) view.findViewById(R.id.tv_no_activities);

        getActivities();
    }

    private void getActivities() {
        Date d = new Date();
        CharSequence date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());
        userRef = mSharedPreferences.getString(ConstantValues.USER_REF, "");
        String guid = mSharedPreferences.getString(ConstantValues.GUID, "");
        String url = ConstantValues.BASE_URL + "kidActivityInformation/v1/getKidActivityInformation?requestedOn=" + date + "&requestedFrom=Mobile&geoLocation=" + mLatitude + "," + mLongitude + "&userRef=" + userRef + "&guid=" + guid;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new AddHeaderInterceptor());

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(ConstantValues.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();
        ActivitiesListService client = retrofit.create(ActivitiesListService.class);
        Call<ActivityResModel> call = client.getActivities(url);
        call.enqueue(new Callback<ActivityResModel>() {
            @Override
            public void onResponse(Call<ActivityResModel> call, retrofit2.Response<ActivityResModel> response) {
                if (response.body() != null) {
                    Log.e("res", response.body().toString());

                    if (response.body() instanceof ActivityResModel) {
                        ActivityResModel activityResModel = response.body();
                        if (activityResModel != null && activityResModel.getArrKids() != null && activityResModel.getArrKids().size() > 0) {
                            activitiesAdapter = new ActivitiesAdapter(getActivity(), activityResModel.getArrKids());
                            activitiesAdapter.setClickListener(ActivitiesFragment.this);
                            rvKidActivity.setVisibility(View.VISIBLE);
                            tvNoActivities.setVisibility(View.GONE);
                            rvKidActivity.setAdapter(activitiesAdapter);
                            rvKidActivity.setLayoutManager(new LinearLayoutManager(getActivity()));
                        } else {
                            rvKidActivity.setVisibility(View.GONE);
                            tvNoActivities.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    mSharedPreferences.edit().putBoolean(ConstantValues.IS_LOGIN, false).apply();
                    mSharedPreferences.edit().putString(ConstantValues.USER_REF, "").apply();
                    mSharedPreferences.edit().putString(ConstantValues.TOKEN, "").apply();
                    mSharedPreferences.edit().putString(ConstantValues.GUID, "").apply();

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }

            }

            @Override
            public void onFailure(Call<ActivityResModel> call, Throwable t) {
                Log.e("error", t.toString());
                rvKidActivity.setVisibility(View.GONE);
                tvNoActivities.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public void onItemClick(Object object, long kidUserID) {
        if (object instanceof ActivityResModel.ActivityData) {
            ActivityResModel.ActivityData activityData = (ActivityResModel.ActivityData) object;
            if (activityData != null) {
                String webUrl = "http://49.207.0.196:8888/kidactivity1.html?SchoolUniqueId=" + activityData.getSchoolUniqueId() + "&userRef=" + activityData.getTeacheruserref() + "&tid=" + activityData.getTemplateID() + "&token=Bearer%20" + token + "&kiduserID=" + kidUserID + "&activityID=" + activityData.getActivityID();
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", webUrl);
                startActivity(intent);
            }
        }

    }

    public class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("Authorization", "Bearer " + token);

            return chain.proceed(builder.build());
        }
    }
}
