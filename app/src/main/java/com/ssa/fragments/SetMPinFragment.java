package com.ssa.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ssa.LoginActivity;
import com.ssa.R;
import com.ssa.TabsActivity;
import com.ssa.utils.ConstantValues;
import com.ssa.utils.Utils;

public class SetMPinFragment extends Fragment {

    private EditText etPin, etConfirmPin;
    private Button btnConfirm;
    private String pin = "", confirmPin = "";
    private SharedPreferences mSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_mpin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);

        etPin = (EditText) view.findViewById(R.id.et_pin);
        etConfirmPin = (EditText) view.findViewById(R.id.et_confirm_pin);
        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pin = etPin.getText().toString().trim();
                confirmPin = etConfirmPin.getText().toString().trim();

                if (TextUtils.isEmpty(pin)) {
                    etPin.setError(getString(R.string.please_enter_pin));
                    return;
                }

                if (pin.length() != 4) {
                    Utils.showAlertWithMessage(getActivity(), getString(R.string.please_enter_four_pin));
                    return;
                }

                if (TextUtils.isEmpty(confirmPin)) {
                    etConfirmPin.setError(getString(R.string.please_enter_confirm_pin));
                    return;
                }

                if (!pin.equalsIgnoreCase(confirmPin)) {
                    Utils.showAlertWithMessage(getActivity(), getString(R.string.pins_doesnot_match));
                    return;
                }
                mSharedPreferences.edit().putString(ConstantValues.MPIN, etPin.getText().toString()).apply();

                Intent intent = new Intent(getActivity(), TabsActivity.class);
                startActivity(intent);
                getActivity().finish();
                if (LoginActivity.instance != null)
                    LoginActivity.instance.finish();
            }
        });

    }
}
