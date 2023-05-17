package com.example.gamezone.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamezone.R;
import com.example.gamezone.databinding.ActivityLoginBinding;
import com.example.gamezone.ui.firebase.Firebase;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private final Firebase firebase = new Firebase();

    CharSequence user = "";
    CharSequence password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setUi();
    }

    private void setUi() {
        setUserTextWatcher(binding.tilEmail);
        setPasswordTextWatcher(binding.tilPassword);

        binding.btnLogin.setOnClickListener(view -> firebase.signIn(user.toString(), password.toString(), this));

        binding.btnRegister.setOnClickListener(view -> {
            firebase.createAccount(user.toString(), password.toString(), this);
            //firebase.signIn(user, password, this);
        });
    }

    private void setUserTextWatcher(TextInputLayout til) {
            Objects.requireNonNull(til.getEditText()).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    user = charSequence;
                    validate();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
    }

    private void setPasswordTextWatcher(TextInputLayout til) {
        Objects.requireNonNull(til.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password = charSequence;
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void validate() {
        binding.btnLogin.setEnabled(user.length() > 0 && password.length() > 0);
        binding.btnRegister.setEnabled(user.length() > 0 && password.length() > 0);

    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.checkUser();
    }


}