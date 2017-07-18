package com.ssa.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ssa.LoginActivity;
import com.ssa.R;
import com.ssa.TabsActivity;
import com.ssa.utils.ConstantValues;

public class MPinFragment extends Fragment {

    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv0/*, tvBack, tvClear*/;
    private EditText etMPin;
    private String mPin = "";
    private SharedPreferences mSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enter_mpin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);

        tv1 = (TextView) view.findViewById(R.id.tv_1);
        tv2 = (TextView) view.findViewById(R.id.tv_2);
        tv3 = (TextView) view.findViewById(R.id.tv_3);
        tv4 = (TextView) view.findViewById(R.id.tv_4);
        tv5 = (TextView) view.findViewById(R.id.tv_5);
        tv6 = (TextView) view.findViewById(R.id.tv_6);
        tv7 = (TextView) view.findViewById(R.id.tv_7);
        tv8 = (TextView) view.findViewById(R.id.tv_8);
        tv9 = (TextView) view.findViewById(R.id.tv_9);
        tv0 = (TextView) view.findViewById(R.id.tv_0);
//        tvBack = (TextView) view.findViewById(R.id.tv_back);
//        tvClear = (TextView) view.findViewById(R.id.tv_clear);

        etMPin = (EditText) view.findViewById(R.id.et_mpin);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin += "1";
                etMPin.setText(mPin);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin += "2";
                etMPin.setText(mPin);
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin += "3";
                etMPin.setText(mPin);
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin += "4";
                etMPin.setText(mPin);
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin += "5";
                etMPin.setText(mPin);
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin += "6";
                etMPin.setText(mPin);
            }
        });
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin += "7";
                etMPin.setText(mPin);
            }
        });
        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin += "8";
                etMPin.setText(mPin);
            }
        });
        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin = "9";
                etMPin.setText(mPin);
            }
        });
        tv0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin += "0";
                etMPin.setText(mPin);
            }
        });

        /*tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPin.length() > 0) {
                    mPin = mPin.substring(0, mPin.length() - 1);
                    etMPin.setText(mPin);
                }
            }
        });

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPin = "";
                etMPin.setText("");
            }
        });*/

        etMPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    if (s.toString().equals(mSharedPreferences.getString(ConstantValues.MPIN, ""))) {
                        Intent intent = new Intent(getActivity(), TabsActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        if (LoginActivity.instance != null)
                            LoginActivity.instance.finish();
                    } else {
                        mPin = "";
                        etMPin.setText("");
                        Toast.makeText(getActivity(), getString(R.string.wrong_pin), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
