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

    String user = "";
    String password = "";

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

        binding.btnLogin.setOnClickListener(view -> firebase.signIn(user, password, this));

        binding.btnRegister.setOnClickListener(view -> {
            firebase.createAccount(user, password, this);
            //firebase.signIn(user, password, this);
        });
    }

    private void setUserTextWatcher(TextInputLayout til) {
            String[] editText = new String[1];
            Objects.requireNonNull(til.getEditText()).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editText[0] = charSequence.toString();
                    user = Arrays.toString(editText);
                    validate();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
    }

    private void setPasswordTextWatcher(TextInputLayout til) {
        String[] editText = new String[1];
        Objects.requireNonNull(til.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editText[0] = charSequence.toString();
                password = Arrays.toString(editText);
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void validate() {
        if(!user.isEmpty() && !password.isEmpty()) {
            binding.btnLogin.setEnabled(true);
        } else {
            binding.btnLogin.setEnabled(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.checkUser();
    }


}