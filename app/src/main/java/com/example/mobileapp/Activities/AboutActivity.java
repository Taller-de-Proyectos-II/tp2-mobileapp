package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mobileapp.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{

    String passedUser, passedPassword;
    ImageView ivLinkedRoger, ivLinkedNathaly, ivLinkedRenato;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Acerca De");

        ivLinkedRoger = findViewById(R.id.ivLinkedRoger);
        ivLinkedRoger.setOnClickListener(this);
        ivLinkedNathaly = findViewById(R.id.ivLinkedNathaly);
        ivLinkedNathaly.setOnClickListener(this);
        ivLinkedRenato = findViewById(R.id.ivLinkedRenato);
        ivLinkedRenato.setOnClickListener(this);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);


        Intent intent = getIntent();
        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
            passedPassword = intent.getStringExtra("password");
        }
    }

    private void showPopup(View v) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivLinkedRenato:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.linkedin.com/in/renato-ballon-0681a1120/"));
                startActivity(i);
                break;
            case R.id.ivLinkedNathaly:
                Intent j = new Intent(Intent.ACTION_VIEW);
                j.setData(Uri.parse("https://www.linkedin.com/in/nathaly-gamarra-santos-99a59314a"));
                startActivity(j);
                break;
            case R.id.ivLinkedRoger:
                Intent k = new Intent(Intent.ACTION_VIEW);
                k.setData(Uri.parse("https://www.linkedin.com/in/roger-lópez-trujillo-499a07141"));
                startActivity(k);
                break;
            case R.id.btnBack:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),MenuActivity.class).putExtra("DNI", passedUser).putExtra("password", passedPassword);
                        mp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mp);
                    }
                }, 1000);
                break;

        }
    }
}
