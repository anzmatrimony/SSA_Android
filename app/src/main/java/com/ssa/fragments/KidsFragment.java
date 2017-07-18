package com.ssa.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
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

public class KidsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int REQUEST_LOCATION = 0;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected Location mCurrentLocation;
    private String mLatitude, mLongitude;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            try {
                mCurrentLocation = location;
                if (mCurrentLocation != null) {
                    double latitude = mCurrentLocation.getLatitude();
                    double longitude = mCurrentLocation.getLongitude();
                    mLatitude = String.valueOf(latitude);
                    mLongitude = String.valueOf(longitude);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private KidAdapter kidAdapter;
    private RecyclerView rvKids;
    private TextView tvAddKid;
    private ImageView ivAddKid;
    private SharedPreferences mSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kids, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);

        rvKids = (RecyclerView) view.findViewById(R.id.rv_kids);
        tvAddKid = (TextView) view.findViewById(R.id.tv_add_kid);
        ivAddKid = (ImageView) view.findViewById(R.id.iv_add_kid);

        requestLocationPermission();
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();

        getKids();

        tvAddKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddKidFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment, "Add Kid");
                transaction.addToBackStack("Add Kid");
                transaction.commit();
            }
        });

        ivAddKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddKidFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment, "Add Kid");
                transaction.addToBackStack("Add Kid");
                transaction.commit();
            }
        });
    }

    private void getKids() {
        Date d = new Date();
        CharSequence date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());
        String userRef = mSharedPreferences.getString(ConstantValues.USER_REF, "");
        String guid = mSharedPreferences.getString(ConstantValues.GUID, "");
        String url = ConstantValues.BASE_URL + "kidList/v1/kidList?parentUserRef=" + userRef + "&userRef=" + userRef + "&requestedon=" + date + "&requestedfrom=Mobile&guid="
                + guid + "&geolocation=" + mLatitude + "," + mLongitude;

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
                                kidAdapter = new KidAdapter(getActivity(), arrKids, false, true);
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
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            return;
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {

            mLatitude = location.getLatitude() + "";
            mLongitude = location.getLongitude() + "";

        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i("", "All location settings are satisfied.");
                getCurrentLocation();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i("", "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i("", "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i("", "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }

    private void requestLocationPermission() {
        Log.i("", "CAMERA permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i("",
                    "Displaying camera permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION);
            /*Snackbar.make(findViewById(R.id.tv_capture), R.string.permission_location_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();*/
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
        // END_INCLUDE(camera_permission_request)
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(5000);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(1000);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
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
