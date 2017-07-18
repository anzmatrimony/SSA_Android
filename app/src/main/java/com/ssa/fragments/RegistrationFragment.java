package com.ssa.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.ssa.LoginActivity;
import com.ssa.R;
import com.ssa.interfaces.CheckEmailService;
import com.ssa.interfaces.RegisterService;
import com.ssa.interfaces.TokenService;
import com.ssa.models.RegisterModel;
import com.ssa.models.RegisterReqModel;
import com.ssa.models.StatusModel;
import com.ssa.models.TokenModel;
import com.ssa.utils.ConstantValues;
import com.ssa.utils.Utils;

import org.json.JSONObject;

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

public class RegistrationFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult> {

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int REQUEST_LOCATION = 0;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected Location mCurrentLocation;
    private EditText etFirstName, etLastName, etEmail, etPhoneNumber, etPassword, etConfirmPassword;
    private RadioButton rbMale, rbFemale;
    private CheckBox cbAcceptTerms;
    private Button btnRegister;
    private String firstName, lastName, email, phoneNumber, password, confirmPassword;
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
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);

        etFirstName = (EditText) view.findViewById(R.id.et_first_name);
        etLastName = (EditText) view.findViewById(R.id.et_last_name);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etPhoneNumber = (EditText) view.findViewById(R.id.et_phone_number);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        etConfirmPassword = (EditText) view.findViewById(R.id.et_confirm_password);
        rbMale = (RadioButton) view.findViewById(R.id.rb_male);
        rbFemale = (RadioButton) view.findViewById(R.id.rb_female);
        cbAcceptTerms = (CheckBox) view.findViewById(R.id.cb_accpet_terms);
        btnRegister = (Button) view.findViewById(R.id.btn_register);

        String fname = getString(R.string.first_name);
        String colored = " *";
        SpannableStringBuilder builder = new SpannableStringBuilder(fname + colored);

        builder.setSpan(new ForegroundColorSpan(Color.GRAY), fname.length(), builder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        etFirstName.setHint(builder);

        String lname = getString(R.string.last_name);
        builder = new SpannableStringBuilder(lname + colored);

        builder.setSpan(new ForegroundColorSpan(Color.GRAY), lname.length(), builder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        etLastName.setHint(builder);

        String email_id = getString(R.string.email_id);
        builder = new SpannableStringBuilder(email_id + colored);

        builder.setSpan(new ForegroundColorSpan(Color.GRAY), email_id.length(), builder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        etEmail.setHint(builder);

        String phno = getString(R.string.phone_number);
        builder = new SpannableStringBuilder(phno + colored);

        builder.setSpan(new ForegroundColorSpan(Color.GRAY), phno.length(), builder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        etPhoneNumber.setHint(builder);

        String pwd = getString(R.string.password);
        builder = new SpannableStringBuilder(pwd + colored);

        builder.setSpan(new ForegroundColorSpan(Color.GRAY), pwd.length(), builder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        etPassword.setHint(builder);

        String cnfrmpwd = getString(R.string.confirm_password);
        builder = new SpannableStringBuilder(cnfrmpwd + colored);

        builder.setSpan(new ForegroundColorSpan(Color.GRAY), cnfrmpwd.length(), builder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        etConfirmPassword.setHint(builder);

        requestLocationPermission();
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();

        getToken();

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkEmail(etEmail.getText().toString().toLowerCase());
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = etFirstName.getText().toString().trim();
                lastName = etLastName.getText().toString().trim();
                email = etEmail.getText().toString().toLowerCase().trim();
                phoneNumber = etPhoneNumber.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(firstName)) {
                    etFirstName.setError(getString(R.string.please_enter_first_name));
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    etLastName.setError(getString(R.string.please_enter_last_name));
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    etEmail.setError(getString(R.string.please_enter_email));
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError(getString(R.string.please_enter_valid_email));
                    return;
                }

                if (TextUtils.isEmpty(phoneNumber)) {
                    etPhoneNumber.setError(getString(R.string.please_enter_phone_number));
                    return;
                }

                if (phoneNumber.length() != 10) {
                    etPhoneNumber.setError(getString(R.string.please_enter_valid_phone_number));
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError(getString(R.string.please_enter_password));
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    etConfirmPassword.setError(getString(R.string.please_enter_confrim_password));
                    return;
                }

                if (!password.equalsIgnoreCase(confirmPassword)) {
                    Utils.showAlertWithMessage(getActivity(), getString(R.string.passwords_doesnot_match));
                    return;
                }

                if (!cbAcceptTerms.isChecked()) {
                    Utils.showAlertWithMessage(getActivity(), getString(R.string.please_accept_terms));
                    return;
                }

                register();
            }
        });

    }

    private void getToken() {
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

        TokenService client = retrofit.create(TokenService.class);
        Call<TokenModel> call = client.getToken("client_credentials");
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, retrofit2.Response<TokenModel> response) {
                Log.e("res", response.body().toString());

                if (response.body() instanceof TokenModel) {
                    TokenModel tokenModel = response.body();
                    if (tokenModel != null) {
                        Log.e("token", tokenModel.getAccessToken());
                        mSharedPreferences.edit().putString(ConstantValues.TOKEN, tokenModel.getAccessToken()).apply();
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });

    }

    public void checkEmail(String email) {
        Date d = new Date();
        CharSequence date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());
        String guid = mSharedPreferences.getString(ConstantValues.GUID, "");
        String url = ConstantValues.BASE_URL + "schoolRegistration/1.0/emailValidation?requestedon=" + date + "&requestedfrom=Mobile&guid=" + guid + "&emailID=" + email + "&geolocation=" + mLatitude + "," + mLongitude;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new AddHeaderInterceptorEmail());

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

        CheckEmailService client = retrofit.create(CheckEmailService.class);
        Call<StatusModel> call = client.checkEmail(url);
        call.enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, retrofit2.Response<StatusModel> response) {
                if (response.body() != null) {
                    Log.e("res", response.body().toString());

                    if (response.body() instanceof StatusModel) {
                        StatusModel statusModel = response.body();
                        if (statusModel != null && statusModel.getStatusCode() != null && !statusModel.getStatusCode().equalsIgnoreCase("-1"))
                            etEmail.setError(getString(R.string.email_already_exists));
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });

    }

    private void register() {

        String url = ConstantValues.BASE_URL;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new AddHeaderInterceptorEmail());

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

        RegisterService client = retrofit.create(RegisterService.class);
        JSONObject jsonObject = new JSONObject();
        JSONObject bodyObj = new JSONObject();
        JSONObject headerObj = new JSONObject();
        RegisterReqModel registerReqModel = new RegisterReqModel();
        RegisterReqModel.Body body = registerReqModel.new Body();
        RegisterReqModel.Header header = registerReqModel.new Header();

        body.setGender(rbMale.isChecked() ? "Male" : "Female");
        body.setEmailId(email);
        body.setFirstName(etFirstName.getText().toString().trim());
        body.setLastName(etLastName.getText().toString().trim());
        body.setPassword(etPassword.getText().toString().trim());
        body.setPhoneNumber(etPhoneNumber.getText().toString().trim());
        body.setUserId(email);

        Date d = new Date();
        CharSequence date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());

        header.setGeolocation(mLatitude + "," + mLongitude);
        header.setRequestedfrom("Mobile");
        header.setRequestedon(String.valueOf(date));
        header.setUserRef(email.split("@")[0] + new Date().getTime());

        registerReqModel.setBody(body);
        registerReqModel.setHeader(header);
        try {
            bodyObj.put("Gender", rbMale.isChecked() ? "Male" : "Female");
            bodyObj.put("emailId", email);
            bodyObj.put("firstName", etFirstName.getText().toString().trim());
            bodyObj.put("lastName", etLastName.getText().toString().trim());
            bodyObj.put("password", etPassword.getText().toString().trim());
            bodyObj.put("phoneNumber", etPhoneNumber.getText().toString().trim());
            bodyObj.put("userId", email);

            headerObj.put("geolocation", mLatitude + "," + mLongitude);
            headerObj.put("requestedfrom", "Mobile");

            headerObj.put("requestedon", date);
            headerObj.put("userRef", email.split("@")[0] + new Date().getTime());

            jsonObject.put("body", bodyObj);
            jsonObject.put("header", headerObj);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<RegisterModel> call = client.registerUser(registerReqModel);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, retrofit2.Response<RegisterModel> response) {
                if (response.body() != null) {
                    Log.e("reg_res", response.body().toString());

                    if (response.body() instanceof RegisterModel) {
                        RegisterModel registerModel = response.body();
                        if (registerModel.getBody().getMessage().equalsIgnoreCase("Registered Successfully")) {
                            Toast.makeText(getActivity(), registerModel.getBody().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
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

    public class AddHeaderInterceptorEmail implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("Authorization", "Bearer " + mSharedPreferences.getString(ConstantValues.TOKEN, ""));

            return chain.proceed(builder.build());
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

}
