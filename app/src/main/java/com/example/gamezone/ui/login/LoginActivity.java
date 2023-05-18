package com.example.gamezone.ui.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamezone.R;
import com.example.gamezone.databinding.ActivityLoginBinding;
import com.example.gamezone.ui.MainActivity;
import com.example.gamezone.ui.firebase.Firebase;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private final Firebase firebase = new Firebase();

    private static final String PASSWORD_REGEX =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,12}$";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(PASSWORD_REGEX);

    CharSequence user = "";
    CharSequence password = "";

    com.example.gamezone.ui.sharedpreferences.SharedPreferences sharedPreferences = new com.example.gamezone.ui.sharedpreferences.SharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checkCredentials();

        setUi();
    }

    private void setUi() {
        setUserTextWatcher(binding.tilEmail);
        setPasswordTextWatcher(binding.tilPassword);

        binding.btnLogin.setOnClickListener(view -> {
            firebase.signIn(user.toString(), password.toString(), this);
            rememberCredentials();
            goToMainActivity();
        });

        binding.btnRegister.setOnClickListener(view -> {
            if (PASSWORD_PATTERN.matcher(password.toString()).matches()) {
                firebase.createAccount(user.toString(), password.toString(), this);

            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(R.string.dialog_password_text);
                dialog.setPositiveButton(R.string.dialog_ok_button, (dialogInterface, i) -> dialogInterface.dismiss());
                dialog.create();
                dialog.show();
            }
        });
    }

    private void checkCredentials() {
        user = sharedPreferences.getPrefs("user", this);
        password = sharedPreferences.getPrefs("password", this);

        if (user.length() > 0 && password.length() > 0) {
            firebase.signIn(user.toString(), password.toString(), this);
            goToMainActivity();
        }
    }

    private void rememberCredentials() {
        if (binding.cbRememberCredentials.isChecked()) {
            sharedPreferences.savePrefs(user.toString(), password.toString(), this);
        }
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

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}