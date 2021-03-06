package br.edu.unis.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final String SP_LOGIN = "credential";
    private static final String USER = "test";
    private static final String PASSWORD = "test";
    private EditText edtUser;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (hasCredentialsSaved()) {
            goDashboardActivity();
        }
        loadWidgets();
    }

    private boolean hasCredentialsSaved() {
        return this.getSharedPreferences(SP_LOGIN, MODE_PRIVATE).contains(SP_LOGIN);
    }

    private void goDashboardActivity() {
        startActivity(new Intent(this, DashboardActivity.class));
    }

    private void loadWidgets() {
        this.edtUser = findViewById(R.id.login_edt_user);
        this.edtUser.requestFocus();
        this.edtPassword = findViewById(R.id.login_edt_password);
        Button btnLogin = findViewById(R.id.login_btn_login);
        btnLogin.setOnClickListener(view -> login());
    }

    private void login() {
        hideKeyboard();
        if (this.isFieldsEmpty()) {
            Toast.makeText(this, R.string.login_txt_fields_required, Toast.LENGTH_SHORT).show();
            this.edtUser.requestFocus();
            return;
        }
        if (!this.authenticate()) {
            Toast.makeText(this, R.string.login_txt_credentials_wrong, Toast.LENGTH_SHORT).show();
            this.edtUser.requestFocus();
            return;
        }
        saveCredentials();
        goDashboardActivity();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtUser.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);
    }

    private boolean isFieldsEmpty() {
        return this.edtUser.getText().toString().isEmpty()
            || this.edtPassword.getText().toString().isEmpty();
    }

    private boolean authenticate() {
        String user = this.edtUser.getText().toString();
        String password = this.edtPassword.getText().toString();
        if (user.equals(LoginActivity.USER) && password.equals(LoginActivity.PASSWORD)) {
            return true;
        }
        cleanFields();
        return false;
    }

    private void saveCredentials() {
        SharedPreferences.Editor editor = this.getSharedPreferences(SP_LOGIN, MODE_PRIVATE).edit();
        editor.putString(SP_LOGIN, edtUser.getText().toString()).apply();
    }

    private void cleanFields() {
        this.edtUser.setText("");
        this.edtPassword.setText("");
        this.edtUser.requestFocus();
    }
}