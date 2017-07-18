package com.ssa.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ssa.R;
import com.ssa.interfaces.ForgotPwdService;
import com.ssa.interfaces.TokenService;
import com.ssa.models.ForgotPwdModel;
import com.ssa.models.ForgotPwdResModel;
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

public class ForgotPasswordFragment extends Fragment {

    private Button btnSubmit;
    private EditText etEmail;
    private String email;
    private SharedPreferences mSharedPreferences;
    private String mLatitude = "0.0", mLongitude = "0.0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);

        etEmail = (EditText) view.findViewById(R.id.et_email);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);

        getToken();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString().toLowerCase().trim();

                if (TextUtils.isEmpty(email)) {
                    etEmail.setError(getString(R.string.please_enter_email));
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError(getString(R.string.please_enter_valid_email));
                    return;
                }

                forgotPassword();
            }
        });
    }

    private void forgotPassword() {
        String url = ConstantValues.BASE_URL + "forgetpasword/1.0/forgotPassword";

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

        ForgotPwdService client = retrofit.create(ForgotPwdService.class);
        final ForgotPwdModel forgotPwdModel = new ForgotPwdModel();
        ForgotPwdModel.Body body = forgotPwdModel.new Body();
        ForgotPwdModel.Header header = forgotPwdModel.new Header();

        body.setEmailId(email);

        Date d = new Date();
        CharSequence date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());

        header.setGeoLocation(mLatitude + "," + mLongitude);
        header.setRequestedFrom("Mobile");
        header.setRequestedOn(String.valueOf(date));
        header.setGuid(email.split("@")[0] + new Date().getTime());

        forgotPwdModel.setBody(body);
        forgotPwdModel.setHeader(header);


        Call<ForgotPwdResModel> call = client.forgotPwd(forgotPwdModel);

        call.enqueue(new Callback<ForgotPwdResModel>() {
            @Override
            public void onResponse(Call<ForgotPwdResModel> call, retrofit2.Response<ForgotPwdResModel> response) {
                if (response.body() != null) {
                    Log.e("reg_res", response.body().toString());

                    if (response.body() instanceof ForgotPwdResModel) {
                        ForgotPwdResModel forgotPwdResModel = response.body();
                        if (forgotPwdResModel.getHeader() != null && forgotPwdResModel.getHeader().getStatus() != null && forgotPwdResModel.getHeader().getStatus().equalsIgnoreCase("success")) {
                            Toast.makeText(getActivity(), getString(R.string.reset_pwd_mail_sent), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPwdResModel> call, Throwable t) {
                Log.e("error", t.toString());

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

    public class AddHeaderInterceptorEmail implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("Authorization", "Bearer " + mSharedPreferences.getString(ConstantValues.TOKEN, ""));

            return chain.proceed(builder.build());
        }
    }
}
