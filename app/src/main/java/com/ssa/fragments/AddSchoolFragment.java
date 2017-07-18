package com.ssa.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssa.LoginActivity;
import com.ssa.R;
import com.ssa.interfaces.AddSchoolGetService;
import com.ssa.interfaces.AddSchoolPostService;
import com.ssa.models.AddSchoolReqModel;
import com.ssa.models.SchoolsListModel;
import com.ssa.utils.ConstantValues;

import org.json.JSONException;
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

public class AddSchoolFragment extends Fragment {

    private ImageView ivAddSchool, ivConfirmSchool;
    private EditText etUid;
    private Button btnSubmit;
    private String uid = "";
    private SharedPreferences mSharedPreferences;
    private String mLatitude = "0.0", mLongitude = "0.0";
    private boolean isConfirmSchool = false;
    private LinearLayout llUID, llDetails;
    private TextView tvNameofSchool, tvCity, tvState, tvPincode, tvWebsite;
    private SchoolsListModel schoolsListModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_schools, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);

        llUID = (LinearLayout) view.findViewById(R.id.ll_uid);
        llDetails = (LinearLayout) view.findViewById(R.id.ll_details);
        ivAddSchool = (ImageView) view.findViewById(R.id.iv_add_school);
        ivConfirmSchool = (ImageView) view.findViewById(R.id.iv_confirm_school);
        etUid = (EditText) view.findViewById(R.id.et_uid);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        tvNameofSchool = (TextView) view.findViewById(R.id.tv_name_of_school);
        tvCity = (TextView) view.findViewById(R.id.tv_city);
        tvState = (TextView) view.findViewById(R.id.tv_state);
        tvPincode = (TextView) view.findViewById(R.id.tv_pincode);
        tvWebsite = (TextView) view.findViewById(R.id.tv_website);

        ivAddSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConfirmSchool) {
                    isConfirmSchool = false;
                    ivAddSchool.setImageResource(R.drawable.ic_add_school_select);
                    ivConfirmSchool.setImageResource(R.drawable.ic_confirm_school);
                    llUID.setVisibility(View.VISIBLE);
                    llDetails.setVisibility(View.GONE);
                    tvNameofSchool.setText("");
                    tvCity.setText("");
                    tvState.setText("");
                    tvPincode.setText("");
                    tvWebsite.setText("");
                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isConfirmSchool) {
                    uid = etUid.getText().toString().trim();
                    if (TextUtils.isEmpty(uid)) {
                        etUid.setError(getString(R.string.please_enter_uid));
                        return;
                    }

                    addSchoolGet();
                } else {
                    addSchoolPost();
                }
            }
        });

    }

    private void addSchoolPost() {
        String userRef = mSharedPreferences.getString(ConstantValues.USER_REF, "");
        String guid = mSharedPreferences.getString(ConstantValues.GUID, "");
        String url = ConstantValues.BASE_URL;

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

        JSONObject jsonObject = new JSONObject();
        JSONObject bodyObj = new JSONObject();
        JSONObject headerObj = new JSONObject();

        AddSchoolReqModel addSchoolReqModel = new AddSchoolReqModel();
        AddSchoolReqModel.Body bodyModel = addSchoolReqModel.new Body();
        AddSchoolReqModel.Header headerModel = addSchoolReqModel.new Header();

        if (schoolsListModel != null) {

            headerModel.setRequestedfrom(schoolsListModel.getHeader().getRequestedfrom());
            headerModel.setRequestedon(schoolsListModel.getHeader().getRequestedon());
            headerModel.setParentUserRef(schoolsListModel.getHeader().getParentUserRef());
            headerModel.setUserRef(schoolsListModel.getHeader().getUserRef());
            headerModel.setGuid(schoolsListModel.getHeader().getGuid());

            bodyModel.setModifiedOn(schoolsListModel.getBody().getModifiedOn());
            bodyModel.setModifiedBy(schoolsListModel.getBody().getModifiedBy());
            bodyModel.setCreatedBy(schoolsListModel.getBody().getCreatedBy());
            bodyModel.setCreatedOn(schoolsListModel.getBody().getCreatedOn());
            bodyModel.setParentUserRef(schoolsListModel.getHeader().getParentUserRef());
            bodyModel.setSchoolUniqueId(schoolsListModel.getBody().getSchoolUniqueId());
            bodyModel.setSchoolName(schoolsListModel.getBody().getSchoolName());
            bodyModel.setParentSchoolId(schoolsListModel.getHeader().getSchoolUniqueId());
            bodyModel.setUserRef(schoolsListModel.getHeader().getUserRef());
            bodyModel.setCity(schoolsListModel.getBody().getCity());
            bodyModel.setState(schoolsListModel.getBody().getState());
            bodyModel.setFirstName(schoolsListModel.getBody().getFirstName());
            bodyModel.setLastName(schoolsListModel.getBody().getLastName());
            bodyModel.setAddress(schoolsListModel.getBody().getAddress());
            bodyModel.setTitle(schoolsListModel.getBody().getTitle());
            bodyModel.setEmailId(schoolsListModel.getBody().getEmailId());
            bodyModel.setZip(schoolsListModel.getBody().getZip());

            addSchoolReqModel.setBody(bodyModel);
            addSchoolReqModel.setHeader(headerModel);

            try {
                headerObj.put("requestedfrom", schoolsListModel.getHeader().getRequestedfrom());
                headerObj.put("requestedon", schoolsListModel.getHeader().getRequestedon());
                headerObj.put("parentUserRef", schoolsListModel.getHeader().getParentUserRef());
                headerObj.put("guid", schoolsListModel.getHeader().getGuid());
                headerObj.put("userRef", schoolsListModel.getHeader().getUserRef());

                bodyObj.put("modifiedOn", schoolsListModel.getBody().getModifiedOn());
                bodyObj.put("modifiedBy", schoolsListModel.getBody().getModifiedBy());
                bodyObj.put("createdBy", schoolsListModel.getBody().getCreatedBy());
                bodyObj.put("parentUserRef", schoolsListModel.getHeader().getParentUserRef());
                bodyObj.put("SchoolUniqueId", schoolsListModel.getBody().getSchoolUniqueId());
                bodyObj.put("SchoolName", schoolsListModel.getBody().getSchoolName());
                bodyObj.put("createdOn", schoolsListModel.getBody().getCreatedOn());
                bodyObj.put("ParentSchoolId", schoolsListModel.getHeader().getSchoolUniqueId());//need to ask
                bodyObj.put("userRef", schoolsListModel.getHeader().getUserRef());
                bodyObj.put("city", schoolsListModel.getBody().getCity());
                bodyObj.put("state", schoolsListModel.getBody().getState());
                bodyObj.put("FirstName", schoolsListModel.getBody().getFirstName());
                bodyObj.put("LastName", schoolsListModel.getBody().getLastName());
                bodyObj.put("Address", schoolsListModel.getBody().getAddress());
                bodyObj.put("title", schoolsListModel.getBody().getTitle());
                bodyObj.put("EmailId", schoolsListModel.getBody().getEmailId());
                bodyObj.put("zip", schoolsListModel.getBody().getZip());

                jsonObject.put("header", headerObj);
                jsonObject.put("body", bodyObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        AddSchoolPostService client = retrofit.create(AddSchoolPostService.class);
        Call<SchoolsListModel> call = client.addSchool(addSchoolReqModel);
        call.enqueue(new Callback<SchoolsListModel>() {
            @Override
            public void onResponse(Call<SchoolsListModel> call, retrofit2.Response<SchoolsListModel> response) {
                if (response.body() != null) {
                    Log.e("res", response.body().toString());

                    if (response.body() instanceof SchoolsListModel) {
                        SchoolsListModel schoolsListModel = response.body();
                        if (schoolsListModel != null) {
                            Toast.makeText(getActivity(), getString(R.string.school_added_scuessfully), Toast.LENGTH_SHORT).show();
                            Fragment fragment = new SchoolsFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.container, fragment, "School");
                            transaction.addToBackStack("School");
                            transaction.commit();
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
            public void onFailure(Call<SchoolsListModel> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    private void addSchoolGet() {
        Date d = new Date();
        CharSequence date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());
        String userRef = mSharedPreferences.getString(ConstantValues.USER_REF, "");
        String guid = mSharedPreferences.getString(ConstantValues.GUID, "");
        String url = ConstantValues.BASE_URL + "addingSchools/v1/parentSchoolRegistration?userRef=" + userRef + "&SchoolUniqueId=" + etUid.getText().toString() + "&requestedon=" + date + "&requestedfrom=Mobile&guid="
                + guid + "&parentUserRef=" + userRef + "&geolocation=" + mLatitude + "," + mLongitude;

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

        AddSchoolGetService client = retrofit.create(AddSchoolGetService.class);
        Call<SchoolsListModel> call = client.addSchool(url);
        call.enqueue(new Callback<SchoolsListModel>() {
            @Override
            public void onResponse(Call<SchoolsListModel> call, retrofit2.Response<SchoolsListModel> response) {
                if (response.body() != null) {
                    Log.e("res", response.body().toString());

                    if (response.body() instanceof SchoolsListModel) {
                        SchoolsListModel schoolsListModel = response.body();
                        if (schoolsListModel != null) {
                            if (schoolsListModel.getBody().getMessage() != null) {
                                Toast.makeText(getActivity(), schoolsListModel.getBody().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                AddSchoolFragment.this.schoolsListModel = schoolsListModel;
                                llUID.setVisibility(View.GONE);
                                llDetails.setVisibility(View.VISIBLE);
                                ivAddSchool.setImageResource(R.drawable.ic_add_school);
                                ivConfirmSchool.setImageResource(R.drawable.ic_confirm_school_select);
                                tvNameofSchool.setText(schoolsListModel.getBody().getSchoolName());
                                tvCity.setText(getString(R.string.city) + schoolsListModel.getBody().getCity());
                                tvState.setText(getString(R.string.state) + schoolsListModel.getBody().getState());
                                tvPincode.setText(getString(R.string.pincode) + schoolsListModel.getBody().getZip());
                                tvWebsite.setText(getString(R.string.website) + schoolsListModel.getBody().getWebSite());
                                isConfirmSchool = true;
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
            public void onFailure(Call<SchoolsListModel> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });

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
