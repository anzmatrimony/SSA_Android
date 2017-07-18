package com.ssa;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ssa.bottombar.BottomBar;
import com.ssa.bottombar.OnTabSelectListener;
import com.ssa.fragments.ActivitiesFragment;
import com.ssa.fragments.KidsFragment;
import com.ssa.fragments.MessagesFragment;
import com.ssa.fragments.SchoolsFragment;
import com.ssa.utils.ConstantValues;

public class TabsActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    private Fragment fragment = null;
    private String message = "";
    private TextView tvLogout;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tabs);

        mSharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);

        bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        tvLogout = (TextView) findViewById(R.id.tv_logout);


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (!mSharedPreferences.getBoolean(ConstantValues.IS_PARENT, false)) {
                    bottomBar.setVisibility(View.GONE);

                    fragment = new MessagesFragment();
                } else {
                    switch (tabId) {

                        case R.id.tab_schools:
                            fragment = new SchoolsFragment();
                            message = "Home";
                            break;
                        case R.id.tab_kids:
                            fragment = new KidsFragment();
                            message = "Kids";
                            break;
                        case R.id.tab_activities:
                            fragment = new ActivitiesFragment();
                            message = "Activities";
                            break;
                        case R.id.tab_messages:
                            fragment = new MessagesFragment();
                            message = "Messages";
                            break;
                    }
                }

                FragmentTransaction transaction;
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();

//                Toast.makeText(TabsActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSharedPreferences.edit().putBoolean(ConstantValues.IS_LOGIN, false).apply();
                mSharedPreferences.edit().putString(ConstantValues.USER_REF, "").apply();
                mSharedPreferences.edit().putString(ConstantValues.TOKEN, "").apply();
                mSharedPreferences.edit().putString(ConstantValues.GUID, "").apply();

                Intent intent = new Intent(TabsActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
}
