package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPwActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pw);

        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);

        etEmail = findViewById(R.id.etEmail);

    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }

    private void sendEmail() {
        String email = etEmail.getText().toString().trim();

        if(etEmail.getText().toString().trim().length() > 0 && isValidEmailId(etEmail.getText().toString().trim())) {

            Call<LoginResponse> call = RetrofitClient.getApiLogin().sendEmail(email);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getApplicationContext(), "Email enviado", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ForgotPwActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        String message = response.body().getMessage();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });

        } else
        {
            Toast.makeText(getApplicationContext(), "Ingrese un correo electrónico", Toast.LENGTH_SHORT).show();
        }

    }
}
