package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

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

        etEmail = findViewById(R.id.etEmail);

    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }

    private void sendEmail() {
        String email = etEmail.getText().toString().trim();

        Call<LoginResponse> call = RetrofitClient.getApiLogin().sendEmail(email);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body().getStatus() == 1){
                    Toast.makeText(getApplicationContext(), "Email enviado",Toast.LENGTH_SHORT).show();

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



    }
}
