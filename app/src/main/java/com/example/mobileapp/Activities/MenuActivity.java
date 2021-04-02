package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


import android.content.Intent;


import android.os.Bundle;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;


import com.example.mobileapp.R;


public class MenuActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    ImageView ivPerfil;
    String passedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Menú Principal");
        ivPerfil = findViewById(R.id.ivPerfil);
        ivPerfil.setOnClickListener(this);



        Intent intent = getIntent();

        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivPerfil:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ppa = new Intent(getApplicationContext(),PatientProfileActivity.class).putExtra("DNI", passedUser);
                        startActivity(ppa);
                    }
                }, 1000);
            break;
        }
    }
    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
