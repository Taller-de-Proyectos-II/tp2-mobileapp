package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.Model.Symptom;
import com.example.mobileapp.R;

public class SymptomDetails extends AppCompatActivity implements  View.OnClickListener{
    ImageView ivPerfil;
    TextView tvName, tvDesc;
    Symptom symptom;
    CardView card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Enviar reporte");
        ivPerfil = findViewById(R.id.ivPerfil);
        ivPerfil.setOnClickListener(this);

        tvDesc = findViewById(R.id.tvSymptomDescription);
        tvName = findViewById(R.id.tvSymptomName);

        card = findViewById(R.id.cardSymptom);
        card.setOnClickListener(this);


        Intent intent = getIntent();
        if(intent.getExtras() != null){
            symptom = (Symptom) intent.getSerializableExtra("data");
            String name = symptom.getName();
            String desc = symptom.getDescription();
            tvDesc.setText(desc);
            tvName.setText(name);
        }
    }

    private void showPopup(View v) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardSymptom:
                Toast.makeText(this, "Envío a backend el reporte del síntoma", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ma = new Intent(getApplicationContext(),ManifestationsActivity.class);
                        startActivity(ma);
                    }
                }, 1000);
                break;
        }
    }
}
