package com.ssa;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ssa.fragments.MPinFragment;
import com.ssa.utils.ConstantValues;

public class MPinActivity extends AppCompatActivity {

    private TextView tvLogout;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mpin);

        if (savedInstanceState == null) {
            Fragment fragment = new MPinFragment();
            Bundle bundle = new Bundle();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();

        }

        mSharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);

        tvLogout = (TextView) findViewById(R.id.tv_logout);

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSharedPreferences.edit().putBoolean(ConstantValues.IS_LOGIN, false).apply();
                mSharedPreferences.edit().putString(ConstantValues.USER_REF, "").apply();
                mSharedPreferences.edit().putString(ConstantValues.TOKEN, "").apply();
                mSharedPreferences.edit().putString(ConstantValues.GUID, "").apply();

                Intent intent = new Intent(MPinActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
