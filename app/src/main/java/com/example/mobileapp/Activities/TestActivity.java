package com.example.mobileapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.Model.Answer;
import com.example.mobileapp.Model.AnswerDTO;
import com.example.mobileapp.Model.Test;
import com.example.mobileapp.Model.testUpdateDTO;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener{

    String passedUser;
    Test test;
    AlertDialog alert1;
    ImageView ivPerfil;
    CardView card;
    TextView tvQuestionName, tvQuestionDesc;
    ArrayList<Answer> answers = new ArrayList<>();
    RadioGroup rgAnswers;
    RadioButton rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4;
    Button btnExit, btnNext;
    testUpdateDTO testAnswers = new testUpdateDTO();
    ArrayList<AnswerDTO> answersDTO = new ArrayList<>();
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });


        card = findViewById(R.id.cardQuestion);
        tvQuestionDesc = findViewById(R.id.tvQuestionDesc);
        tvQuestionName = findViewById(R.id.tvQuestionName);

        rgAnswers = findViewById(R.id.rgAnswers);

        rbAnswer1 = findViewById(R.id.rbAnswer1);
        rbAnswer2 = findViewById(R.id.rbAnswer2);
        rbAnswer3 = findViewById(R.id.rbAnswer3);
        rbAnswer4 = findViewById(R.id.rbAnswer4);

        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            test = (Test) intent.getSerializableExtra("data");
            passedUser = intent.getStringExtra("DNI");
        }

        getSupportActionBar().setTitle("Test de " + test.getTestType());

        for(int i = 0; i < test.getAnswersDTO().size(); i++){
            answers.add(test.getAnswersDTO().get(i));
        }

        if(test.getTestType().equals("Manifestaciones")){
            rbAnswer3.setVisibility(View.INVISIBLE);
            rbAnswer4.setVisibility(View.INVISIBLE);
            rbAnswer2.setText("Sí");
            rbAnswer1.setText("No");
        }


        showQuestion();

    }

    private void showQuestion() {
        if(index < answers.size()) {
            if(test.getTestType().equals("Manifestaciones")) {
                if(index < 4) {
                    String mock = test.getAnswersDTO().get(index).getQuestionDTO().getDescription();
                    tvQuestionDesc.setText(mock);
                }
                if(index == 4){
                    testAnswers.setIdTest(test.getIdTest());
                    testAnswers.setAnswersDTO(answersDTO);
                    btnNext.setVisibility(View.INVISIBLE);
                    sendAnswers();
                }
            } else {
                String mock = test.getAnswersDTO().get(index).getQuestionDTO().getDescription();
                tvQuestionDesc.setText(mock);
            }
        } else{
            testAnswers.setIdTest(test.getIdTest());
            testAnswers.setAnswersDTO(answersDTO);
            btnNext.setVisibility(View.INVISIBLE);
            sendAnswers();
        }
    }

    private void sendAnswers() {
        Call<LoginResponse> sendAnswers = RetrofitClient.getApiTest().sendAnswers(testAnswers);

        sendAnswers.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getStatus() == 1){
                    Toast.makeText(getApplicationContext(), "El test fue enviado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            Intent mp = new Intent(getApplicationContext(),ConfirmationActivity.class).putExtra("DNI", passedUser).putExtra("Codigo", "test");
                            startActivity(mp);
                        }
                    }, 1000);
                } else{
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error! Por favor, realice el test nuevamente.", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            Intent mp = new Intent(getApplicationContext(),TestListActivity.class).putExtra("DNI", passedUser);
                            startActivity(mp);
                        }
                    }, 1000);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }


    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itAlertas:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ma = new Intent(getApplicationContext(),AlertListActivity.class).putExtra("DNI", passedUser);
                        startActivity(ma);
                    }
                }, 1000);
                break;
            case R.id.itMenuPrincipal:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),MenuActivity.class).putExtra("DNI", passedUser);
                        startActivity(mp);
                    }
                }, 1000);
                break;
            case R.id.itContacto:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),ContactPsyActivity.class).putExtra("DNI", passedUser);
                        startActivity(mp);
                    }
                }, 1000);
                break;
            case R.id.itTests:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),TestListActivity.class).putExtra("DNI", passedUser);
                        startActivity(mp);
                    }
                }, 1000);
                break;
            case R.id.itLogout:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent mp = new Intent(getApplicationContext(),MainActivity.class);
                        mp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Toast.makeText(getApplicationContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
                        startActivity(mp);
                    }
                }, 1000);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnExit:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("¿Está seguro de que desea salir? Perderá su progreso");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "CONFIRMAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Handler().postDelayed(new Runnable(){
                                    @Override
                                    public void run(){
                                        Intent mp = new Intent(getApplicationContext(),MenuActivity.class).putExtra("DNI", passedUser);
                                        startActivity(mp);
                                    }
                                }, 1000);
                            }
                        }
                );

                builder1.setNegativeButton(
                        "CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );
                alert1 = builder1.create();
                alert1.show();
                break;
            case R.id.btnNext:
                int score = 0;
                int idAnswer= 0;
                if(rgAnswers.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Seleccione una respuesta por favor", Toast.LENGTH_SHORT).show();
                } else{
                    AnswerDTO mockAns = new AnswerDTO();

                    if(rbAnswer1.isChecked()) {
                        score = 1;
                        if(test.getTestType().equals("Manifestaciones")){
                            score = 0;
                        }
                    } else if(rbAnswer2.isChecked()) {
                        score = 2;
                        if(test.getTestType().equals("Manifestaciones")){
                            score = 1;
                        }
                    } else if(rbAnswer3.isChecked()) {
                        score = 3;
                    } else if(rbAnswer4.isChecked()) {
                        score = 4;
                    }
                    mockAns.setScore(score);
                    mockAns.setIdAnswer(test.getAnswersDTO().get(index).getIdAnswer());
                    answersDTO.add(mockAns);
                    index++;
                    Log.e("HERE", String.valueOf(index));
                    rgAnswers.clearCheck();
                    showQuestion();
                }
                break;

        }
    }
}
