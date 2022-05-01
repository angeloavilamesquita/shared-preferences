package br.edu.unis.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        loadWidgets();
    }

    private void loadWidgets() {
        TextView txtLogout = findViewById(R.id.dashboard_txt_logout);
        txtLogout.setOnClickListener(view -> logout());
    }

    private void logout() {
        SharedPreferences.Editor editor = this.getSharedPreferences(LoginActivity.SP_LOGIN, MODE_PRIVATE)
            .edit();
        editor.clear().apply();
        finish();
    }
}