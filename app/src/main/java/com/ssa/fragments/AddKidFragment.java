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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ssa.LoginActivity;
import com.ssa.R;
import com.ssa.interfaces.AddKidService;
import com.ssa.interfaces.SchoolsListService;
import com.ssa.models.AddKidReqModel;
import com.ssa.models.KidResModel;
import com.ssa.models.SchoolModel;
import com.ssa.utils.ConstantValues;

import org.json.JSONException;
import org.json.JSONObject;

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

public class AddKidFragment extends Fragment {

    private EditText etFirstName, etLastName;
    private Spinner spSchool, spClass, spSection, spRelationship;
    private String[] classArr = new String[]{"Class A", "Class B", "Class C"};
    private String[] sectionArr = new String[]{"Section A", "Section B", "Section C"};
    private String[] relationshipArr = new String[]{"Father", "Mother"};
    private SharedPreferences mSharedPreferences;
    private ArrayList<SchoolModel.Schools> arrSchools = new ArrayList<>();
    private Button btnSubmit;
    private String firstName = "", lastName = "";
    private String mLatitude = "0.0", mLongitude = "0.0", selClass = "", section = "", schoolName = "", schoolId = "", relationship = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_kid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);

        etFirstName = (EditText) view.findViewById(R.id.et_first_name);
        etLastName = (EditText) view.findViewById(R.id.et_last_name);
        spSchool = (Spinner) view.findViewById(R.id.sp_school);
        spClass = (Spinner) view.findViewById(R.id.sp_class);
        spSection = (Spinner) view.findViewById(R.id.sp_section);
        spRelationship = (Spinner) view.findViewById(R.id.sp_relationship);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);

        spClass.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_dropdown_item, classArr));
        spSection.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_dropdown_item, sectionArr));
        spRelationship.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_dropdown_item, relationshipArr));

        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selClass = classArr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schoolName = arrSchools.get(position).getSchoolName();
                schoolId = arrSchools.get(position).getSchoolUniqueId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                section = sectionArr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spRelationship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                relationship = relationshipArr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = etFirstName.getText().toString().trim();
                lastName = etLastName.getText().toString().trim();

                if (TextUtils.isEmpty(firstName)) {
                    etFirstName.setError(getString(R.string.please_enter_first_name));
                    return;
                }
                if (TextUtils.isEmpty(lastName)) {
                    etLastName.setError(getString(R.string.please_enter_last_name));
                    return;
                }

                addKid();
            }
        });

        getSchools();
    }

    private void addKid() {

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

        Date d = new Date();
        CharSequence date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());

        JSONObject jsonObject = new JSONObject();
        JSONObject bodyObj = new JSONObject();
        JSONObject headerObj = new JSONObject();

        try {
            headerObj.put("requestedfrom", "Mobile");
            headerObj.put("guid", mSharedPreferences.getString(ConstantValues.GUID, ""));
            headerObj.put("userRef", mSharedPreferences.getString(ConstantValues.USER_REF, ""));
            headerObj.put("geolocation", mLatitude + "," + mLongitude);

            bodyObj.put("classe", selClass);
            bodyObj.put("firstName", firstName);
            bodyObj.put("lastName", lastName);
            bodyObj.put("section", section);
            bodyObj.put("SchoolName", schoolName);
            bodyObj.put("SchoolUniqueId", schoolId);
            bodyObj.put("kidstatus", "Pending");
            bodyObj.put("createdBy", relationship);

            bodyObj.put("createdOn", date);
            bodyObj.put("parentUserRef", mSharedPreferences.getString(ConstantValues.USER_REF, ""));

            jsonObject.put("header", headerObj);
            jsonObject.put("body", bodyObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AddKidReqModel kidReqModel = new AddKidReqModel();
        AddKidReqModel.Body bodyModel = kidReqModel.new Body();
        AddKidReqModel.Header headerModel = kidReqModel.new Header();

        headerModel.setGeolocation(mLatitude + "," + mLongitude);
        headerModel.setGuid(mSharedPreferences.getString(ConstantValues.GUID, ""));
        headerModel.setUserRef(mSharedPreferences.getString(ConstantValues.USER_REF, ""));
        headerModel.setRequestedfrom("Mobile");

        bodyModel.setClasse(selClass);
        bodyModel.setFirstName(firstName);
        bodyModel.setLastName(lastName);
        bodyModel.setSection(section);
        bodyModel.setSchoolName(schoolName);
        bodyModel.setSchoolUniqueId(schoolId);
        bodyModel.setKidstatus("Pending");
        bodyModel.setCreatedBy(relationship);
        bodyModel.setCreatedOn(String.valueOf(date));
        bodyModel.setParentUserRef(mSharedPreferences.getString(ConstantValues.USER_REF, ""));

        kidReqModel.setBody(bodyModel);
        kidReqModel.setHeader(headerModel);

        AddKidService client = retrofit.create(AddKidService.class);
        Call<KidResModel> call = client.addKid(kidReqModel);
        call.enqueue(new Callback<KidResModel>() {
            @Override
            public void onResponse(Call<KidResModel> call, retrofit2.Response<KidResModel> response) {
                if (response.body() != null) {
                    Log.e("res", response.body().toString());

                    if (response.body() instanceof KidResModel) {
                        KidResModel kidResModel = response.body();
                        if (kidResModel != null) {
                            Toast.makeText(getActivity(), getString(R.string.kid_added_scuessfully), Toast.LENGTH_SHORT).show();
                            Fragment fragment = new KidsFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.container, fragment, "Kid");
                            transaction.addToBackStack("Kid");
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
            public void onFailure(Call<KidResModel> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });

    }

    private void getSchools() {
        Date d = new Date();
        CharSequence date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());
        String userRef = mSharedPreferences.getString(ConstantValues.USER_REF, "");
        String guid = mSharedPreferences.getString(ConstantValues.GUID, "");
        String url = ConstantValues.BASE_URL + "schooList/v1/schoolList?userRef=" + userRef + "&requestedon=" + date + "&requestedfrom=Mobile&guid="
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

        SchoolsListService client = retrofit.create(SchoolsListService.class);
        Call<SchoolModel> call = client.getSchools(url);
        call.enqueue(new Callback<SchoolModel>() {
            @Override
            public void onResponse(Call<SchoolModel> call, retrofit2.Response<SchoolModel> response) {
                if (response.body() != null) {
                    Log.e("res", response.body().toString());

                    if (response.body() instanceof SchoolModel) {
                        SchoolModel schoolModel = response.body();
                        if (schoolModel != null) {
                            arrSchools = schoolModel.getArrSchools();

                            if (arrSchools != null && arrSchools.size() > 0) {
                                spSchool.setAdapter(new ArrayAdapter<SchoolModel.Schools>(getActivity(), R.layout.simple_spinner_dropdown_item, arrSchools));
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
            public void onFailure(Call<SchoolModel> call, Throwable t) {
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
