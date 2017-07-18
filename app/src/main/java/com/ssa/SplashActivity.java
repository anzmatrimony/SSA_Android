package com.ssa;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ssa.utils.ConstantValues;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        mSharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        isLoggedIn = mSharedPreferences.getBoolean(ConstantValues.IS_LOGIN, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn) {
                    Intent intent = new Intent(SplashActivity.this, MPinActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        }, 3000);
    }
}
