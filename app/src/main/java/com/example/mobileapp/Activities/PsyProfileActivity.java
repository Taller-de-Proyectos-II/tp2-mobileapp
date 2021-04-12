package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mobileapp.Model.Psychologist;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.PsychologistResponse;

public class PsyProfileActivity extends AppCompatActivity {

    TextView nombre;
    Psychologist psychologist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_psy);

        nombre = findViewById(R.id.tvPsyName);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            psychologist = (Psychologist) intent.getSerializableExtra("data");
            String name = psychologist.getNames() + " " + psychologist.getLastNames();
            nombre.setText(name);
        }
    }
}
