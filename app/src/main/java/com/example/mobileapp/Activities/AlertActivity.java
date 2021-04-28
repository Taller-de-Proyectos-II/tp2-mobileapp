package com.example.mobileapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.Model.Alert;
import com.example.mobileapp.Model.AlertAnswerDTO;
import com.example.mobileapp.Model.AlertCreateDTO;
import com.example.mobileapp.Model.AlertDTO;
import com.example.mobileapp.Model.AlertUpdateDTO;
import com.example.mobileapp.Model.SymptomDTO;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.AlertResponse;
import com.example.mobileapp.Utils.Responses.AlertResponse2;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    String passedUser;
    RadioButton rbAnswer1, rbAnswer2;
    RadioGroup rgAnswers;
    AlertDialog alert1;
    CardView card;
    Button btnExit, btnNext;
    AlertCreateDTO alertCreateDTO = new AlertCreateDTO();
    TextView tvDescripcion;
    Alert mockAlert = new Alert();
    AlertUpdateDTO alertAnswers = new AlertUpdateDTO();
    ArrayList<AlertDTO> resultados = new ArrayList<>();

    ArrayList<AlertAnswerDTO> preguntas = new ArrayList<>();
    int index = 0;
    int alertValue = 0;
    int idSymptomHolder = 0;
    int idAnswerHolder = 0;
    int alertId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Alerta");

        tvDescripcion = findViewById(R.id.tvSymptomDesc);

        card = findViewById(R.id.symptomCard);
        rgAnswers = findViewById(R.id.rgAnswers);
        rbAnswer1 = findViewById(R.id.rbAnswer1);
        rbAnswer2 = findViewById(R.id.rbAnswer2);



        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
        }
        alertCreateDTO.setPatientDni(passedUser);
        createAlert();
    }

    private void createAlert() {
        Call<AlertResponse> create = RetrofitClient.getApiAlert().createAlert(alertCreateDTO);

        create.enqueue(new Callback<AlertResponse>() {
            @Override
            public void onResponse(Call<AlertResponse> call, Response<AlertResponse> response) {
                if(response.body().getStatus() == 1){
                    alertId = response.body().getAlertDTO().getId();
                    Log.e("ALERT ID", String.valueOf(alertId));
                    Call<AlertResponse2> getInfo = RetrofitClient.getApiAlert().getAlerts(passedUser);
                    getInfo.enqueue(new Callback<AlertResponse2>() {
                        @Override
                        public void onResponse(Call<AlertResponse2> call2, Response<AlertResponse2> response2) {
                            if(response2.body().getStatus() == 1){
                                if(response2.body().getAlertsDTO().size() > 0){
                                    for(int i = 0; i < response2.body().getAlertsDTO().size(); i++){
                                        if (response2.body().getAlertsDTO().get(i).getId() == alertId){
                                            mockAlert.setId(response2.body().getAlertsDTO().get(i).getId());
                                            mockAlert.setDate(response2.body().getAlertsDTO().get(i).getDate());
                                            mockAlert.setImportant(response2.body().getAlertsDTO().get(i).getImportant());
                                            preguntas.addAll(response2.body().getAlertsDTO().get(i).getAlertAnswersDTO());
                                            showQuestion(preguntas, index);

                                        }
                                    }

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AlertResponse2> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<AlertResponse> call, Throwable t) {

            }
        });
    }

    private void showQuestion(ArrayList<AlertAnswerDTO> questions, int index) {
        Log.e("HERE", String.valueOf(preguntas.size()));
        if(index < questions.size()) {
            tvDescripcion.setText(questions.get(index).getSymptomDTO().getDescription());
            idSymptomHolder = questions.get(index).getSymptomDTO().getIdSymptom();
            idAnswerHolder = questions.get(index).getId();
        } else{
            alertAnswers.setIdAlert(alertId);
            alertAnswers.setAlertAnswersDTO(resultados);
            sendAnswers();
        }
    }

    private void sendAnswers() {
        Call<LoginResponse> sendAnswers = RetrofitClient.getApiAlert().sendAnswers(alertAnswers);

        sendAnswers.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getStatus() == 1){
                    if(alertValue >= 4){
                    Toast.makeText(getApplicationContext(), "La alerta fue enviada satisfactoriamente!", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            Intent mp = new Intent(getApplicationContext(),TipsActivity.class).putExtra("DNI", passedUser);
                            startActivity(mp);
                        }
                    }, 1000);
                    } else{
                        Toast.makeText(getApplicationContext(), "La alerta fue enviada satisfactoriamente!", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run(){
                                Intent mp = new Intent(getApplicationContext(),AlertListActivity.class).putExtra("DNI", passedUser);
                                startActivity(mp);
                            }
                        }, 1000);
                    }

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
            case R.id.btnNext:
                int score = 0;
                if(rgAnswers.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Seleccione una respuesta por favor", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDTO mockAlert = new AlertDTO();
                    if(rbAnswer1.isChecked()){
                        score = 1;
                        alertValue++;
                    } else if (rbAnswer2.isChecked()) {
                        score = 0;
                    }
                    mockAlert.setScore(score);
                    mockAlert.setId(idAnswerHolder);
                    resultados.add(mockAlert);
                    index++;
                    rgAnswers.clearCheck();
                    showQuestion(preguntas, index);
                }
                break;
            case R.id.btnExit:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("¿Está seguro de que desea salir? Perderá su progreso");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "CONFIRMAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
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
        }
    }
}
