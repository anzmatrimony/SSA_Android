package com.ssa.fragments;

import android.Manifest;
import android.content.Context;
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
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ssa.ForgotPasswordActivity;
import com.ssa.R;
import com.ssa.RegistrationActivity;
import com.ssa.SetMPinActivity;
import com.ssa.interfaces.LoginService;
import com.ssa.interfaces.LoginTokenService;
import com.ssa.models.LoginModel;
import com.ssa.models.TokenModel;
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

public class LoginFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int REQUEST_LOCATION = 0;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected Location mCurrentLocation;
    private EditText etUserName, etPassword;
    private CheckBox cbRememberMe;
    private TextView tvForgotPassword;
    private Button btnLogin, btnSignUp;
    private String userName = "", password = "";
    private SharedPreferences mSharedPreferences;
    private String mLatitude = "0.0", mLongitude = "0.0";
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);

        etUserName = (EditText) view.findViewById(R.id.et_user_name);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        tvForgotPassword = (TextView) view.findViewById(R.id.tv_forgot_password);
        cbRememberMe = (CheckBox) view.findViewById(R.id.cb_remember_me);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnSignUp = (Button) view.findViewById(R.id.btn_sign_up);

        if (mSharedPreferences.getBoolean(ConstantValues.IS_REMEMBER, false)) {
            etUserName.setText(mSharedPreferences.getString(ConstantValues.USER_NAME, ""));
            etPassword.setText(mSharedPreferences.getString(ConstantValues.PASSWORD, ""));
            etUserName.setSelection(etUserName.getText().length());
            cbRememberMe.setChecked(true);
        } else {
            etUserName.setText("");
            etPassword.setText("");
            cbRememberMe.setChecked(false);
        }

        requestLocationPermission();
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = etUserName.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(userName)) {
                    etUserName.setError(getString(R.string.please_enter_user_name));
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError(getString(R.string.please_enter_password));
                    return;
                }

                getTokenForLogin();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getTokenForLogin() {
        String url = ConstantValues.BASE_URL;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new AddHeaderInterceptor());

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        LoginTokenService client = retrofit.create(LoginTokenService.class);
        Call<TokenModel> call = client.getLoginToken("password", password, userName);
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, retrofit2.Response<TokenModel> response) {
                if (response.body() != null) {
                    Log.e("res", response.body().toString());

                    if (response.body() instanceof TokenModel) {
                        TokenModel tokenModel = response.body();
                        if (tokenModel != null) {
                            Log.e("token", tokenModel.getAccessToken());
                            mSharedPreferences.edit().putString(ConstantValues.TOKEN, tokenModel.getAccessToken()).apply();

                            login();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Log.e("error", t.toString());
                Toast.makeText(getActivity(), getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void login() {
        Date d = new Date();
        CharSequence date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());
        String url = ConstantValues.BASE_URL + "UserProfile/v1/getUserProfile?userId=" + userName + "&requestedOn=" + date + "&requestedFrom=Mobile&guid=1790403434&userRef="
                + userName + "&geoLocation=" + mLatitude + "," + mLongitude;

        Log.e("login_url", url);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new AddHeaderInterceptorToken());

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

        LoginService client = retrofit.create(LoginService.class);
        Call<LoginModel> call = client.login(url);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                if (response.body() != null) {
                    Log.e("res", response.body().toString());

                    if (response.body() instanceof LoginModel) {
                        LoginModel loginModel = response.body();
                        if (loginModel != null) {
                            FirebaseApp.initializeApp(getActivity());
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(userName, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
//                                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                    } else {
//                                        Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            if (loginModel.getBody().getMessage() != null) {
                                Toast.makeText(getActivity(), loginModel.getBody().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (cbRememberMe.isChecked()) {
                                    mSharedPreferences.edit().putBoolean(ConstantValues.IS_REMEMBER, true).apply();
                                }
                                mSharedPreferences.edit().putString(ConstantValues.USER_NAME, userName).apply();
                                mSharedPreferences.edit().putString(ConstantValues.PASSWORD, password).apply();
                                mSharedPreferences.edit().putBoolean(ConstantValues.IS_LOGIN, true).apply();
                                mSharedPreferences.edit().putString(ConstantValues.USER_REF, loginModel.getBody().getUserRef()).apply();
                                mSharedPreferences.edit().putString(ConstantValues.GUID, loginModel.getHeader().getGuid()).apply();

                                if (loginModel.getBody().getRole() != null && loginModel.getBody().getRole().equalsIgnoreCase("parent")) {
                                    mSharedPreferences.edit().putBoolean(ConstantValues.IS_PARENT, true).apply();
                                } else {
                                    mSharedPreferences.edit().putBoolean(ConstantValues.IS_PARENT, false).apply();
                                }
                                Intent intent = new Intent(getActivity(), SetMPinActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
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

    public class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            String string = "jiKoAXEw8OjWbAus_iYTrsdPVG4a:Kf3AksokLYwo6WLigvINp6bVV3Ma";

            String encodedString = Base64.encodeToString(string.getBytes(), Base64.NO_WRAP);
            builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
            builder.addHeader("Authorization", "Basic " + encodedString);

            return chain.proceed(builder.build());
        }
    }

    public class AddHeaderInterceptorToken implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("Authorization", "Bearer " + mSharedPreferences.getString(ConstantValues.TOKEN, ""));

            return chain.proceed(builder.build());
        }
    }

}
