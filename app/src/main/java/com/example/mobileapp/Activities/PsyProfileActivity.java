package com.example.mobileapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobileapp.Model.Psychologist;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.PsychologistResponse;

public class PsyProfileActivity extends AppCompatActivity implements View.OnClickListener{

    TextView nombre;
    Psychologist psychologist;
    Button btnContact;
    AlertDialog alert1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_psy);

        nombre = findViewById(R.id.tvPsyName);

        btnContact = findViewById(R.id.btnContact);
        btnContact.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            psychologist = (Psychologist) intent.getSerializableExtra("data");
            String name = psychologist.getNames() + " " + psychologist.getLastNames();
            nombre.setText(name);
        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Contacto del psicologo " + psychologist.getPhone());
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        alert1 = builder1.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnContact:
                alert1.show();
        }
    }
}
