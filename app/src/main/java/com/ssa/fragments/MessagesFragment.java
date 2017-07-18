package com.ssa.fragments;

import android.app.Activity;
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

import com.ssa.ChatActivity;
import com.ssa.LoginActivity;
import com.ssa.R;
import com.ssa.adapters.KidAdapter;
import com.ssa.interfaces.KidsListService;
import com.ssa.models.KidModel;
import com.ssa.utils.ConstantValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagesFragment extends Fragment implements KidAdapter.OnItemClickListener {

    private RecyclerView rvKids;
    private SharedPreferences mSharedPreferences;
    private String mLatitude = "0.0", mLongitude = "0.0";
    private KidAdapter kidAdapter;
    private boolean isParent = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);

        isParent = mSharedPreferences.getBoolean(ConstantValues.IS_PARENT, false);

        rvKids = (RecyclerView) view.findViewById(R.id.rv_kids);

        getKids();
    }

    private void getKids() {
        Date d = new Date();
        CharSequence date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());
        String userRef = mSharedPreferences.getString(ConstantValues.USER_REF, "");
        String guid = mSharedPreferences.getString(ConstantValues.GUID, "");
        String url = "";

        if (isParent) {
            url = ConstantValues.BASE_URL + "kidList/v1/kidList?parentUserRef=" + userRef + "&userRef=" + userRef + "&requestedon=" + date + "&requestedfrom=Mobile&guid="
                    + guid + "&geolocation=" + mLatitude + "," + mLongitude;
        } else {
            url = ConstantValues.BASE_URL + "kidInformation/v1/getKidInformation?userRef=" + userRef + "&requestedOn=" + date + "&requestedFrom=Mobile&geoLocation=" + mLatitude + "," + mLongitude;
        }

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

        KidsListService client = retrofit.create(KidsListService.class);
        Call<KidModel> call = client.getSchools(url);
        call.enqueue(new Callback<KidModel>() {
            @Override
            public void onResponse(Call<KidModel> call, retrofit2.Response<KidModel> response) {
                if (response.body() != null) {
                    Log.e("res", response.body().toString());

                    if (response.body() instanceof KidModel) {
                        KidModel kidModel = response.body();
                        if (kidModel != null) {
                            ArrayList<KidModel.Kids> arrKids = kidModel.getArrKids();
                            if (arrKids != null && arrKids.size() > 0) {
                                kidAdapter = new KidAdapter(getActivity(), arrKids, true, isParent);
                                kidAdapter.setClickListener(MessagesFragment.this);
                                rvKids.setAdapter(kidAdapter);
                                rvKids.setLayoutManager(new LinearLayoutManager(getActivity()));
                            }
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
            public void onFailure(Call<KidModel> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    @Override
    public void onItemClick(Object object) {
        if (object != null && object instanceof KidModel.Kids) {
            KidModel.Kids kid = (KidModel.Kids) object;
            if (kid != null) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("Kid", kid);
                startActivity(intent);
            }
        }
    }

    public class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("Authorization", "Bearer " + mSharedPreferences.getString(ConstantValues.TOKEN, ""));

            return chain.proceed(builder.build());
        }
    }
}
