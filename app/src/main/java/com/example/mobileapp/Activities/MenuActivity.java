package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mobileapp.R;


public class MenuActivity extends AppCompatActivity {
    TextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        user = findViewById(R.id.tvUsername);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            String passedUser = intent.getStringExtra("data");
            user.setText("Bienvenido"+passedUser);
        }
    }
}
