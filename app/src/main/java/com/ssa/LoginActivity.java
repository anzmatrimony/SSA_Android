package com.ssa;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ssa.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    public static Activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        instance = this;

        if (savedInstanceState == null) {
            Fragment fragment = new LoginFragment();
            Bundle bundle = new Bundle();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();

        }

    }

}
