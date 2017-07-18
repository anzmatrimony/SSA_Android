package com.ssa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ssa.fragments.ChatFragment;
import com.ssa.models.KidModel;
import com.ssa.utils.ConstantValues;

public class ChatActivity extends AppCompatActivity {

    private KidModel.Kids kidId;
    private SharedPreferences mSharedPreferences;
    private boolean isParent = false;
    private String title = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        mSharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);

        isParent = mSharedPreferences.getBoolean(ConstantValues.IS_PARENT, false);

        if (getIntent().hasExtra("Kid"))
            kidId = (KidModel.Kids) getIntent().getSerializableExtra("Kid");

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            if (kidId != null) {
                if (isParent)
                    title = kidId.getFirstName();
                else
                    title = kidId.getKidName();
            }
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        Fragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putSerializable("Kid", kidId);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
