package com.example.mobileapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mobileapp.Model.Psychologist;
import com.example.mobileapp.Model.Schedule;
import com.example.mobileapp.Model.Session;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Adapters.SchedulesAdapter;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.Responses.SchedulesResponse;
import com.example.mobileapp.Utils.RetrofitClient;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsySchedulesActivity extends AppCompatActivity implements  View.OnClickListener, SchedulesAdapter.ClickedItem, PopupMenu.OnMenuItemClickListener {

    RecyclerView rvSchedules;
    ImageView ivToolbar;
    AlertDialog alert1;
    String psyDNI, passedUser;
    SchedulesAdapter schedulesAdapter;
    Psychologist psychologist = new Psychologist();
    String date = "";
    String nombreDia = "";
    Schedule schedule = new Schedule();
    int date2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psy_schedules);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Horarios disponibles");


        rvSchedules = findViewById(R.id.rvPsySchedules);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            psychologist = (Psychologist) intent.getSerializableExtra("data");
            psyDNI = intent.getStringExtra("PSY");
            passedUser = intent.getStringExtra("DNI");

        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3, GridLayoutManager.VERTICAL,false);

        rvSchedules.setLayoutManager(gridLayoutManager);
        rvSchedules.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        schedulesAdapter = new SchedulesAdapter(this::ClickedSche);

        GregorianCalendar today = new GregorianCalendar(GregorianCalendar.getInstance().getTimeZone());
        String test1 = String.valueOf(today.getTimeZone());

        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        date = df.format(current);
        SimpleDateFormat daydf = new SimpleDateFormat("EEEE");
        nombreDia = daydf.format(current);
        String test10 = String.valueOf(current);
        Log.e("TEST FECHA", test10);
        Log.e("TEST FECHA2", nombreDia);

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(df.parse(date));
        } catch (java.text.ParseException e){
            e.printStackTrace();
        }
        date = df.format(c.getTime());
        String[] values = date.split("-");
        switch (nombreDia){
            case "monday":
                date2 = 1;
                break;
            case "tuesday":
                date2 = 2;
                break;
            case "wednesday":
                date2 = 3;
                break;
            case "thursday":
                date2 = 4;
                break;
            case "friday":
                date2 = 5;
                break;
            case "saturday":
                date2 = 6;
                break;
            case "sunday":
                date2 = 7;
                break;
            case "lunes":
                date2 = 1;
                break;
            case "martes":
                date2 = 2;
                break;
            case "miércoles":
                date2 = 3;
                break;
            case "jueves":
                date2 = 4;
                break;
            case "viernes":
                date2 = 5;
                break;
            case "sábado":
                date2 = 6;
                break;
            case "domingo":
                date2 = 7;
                break;
        }

        Log.e("DIA", date);
        Log.e("DIA2", test1);

        fullList();
    }

    private void fullList() {
        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);

        Call<SchedulesResponse> schedules = RetrofitClient.getApiPsychologist().getSchedules(date, psyDNI, token);

        schedules.enqueue(new Callback<SchedulesResponse>() {
            @Override
            public void onResponse(Call<SchedulesResponse> call, Response<SchedulesResponse> response) {
                if(response.isSuccessful()){
                    List<Schedule> scheduleList = response.body().getSchedulesDTO();
                    Collections.sort(scheduleList, new Comparator<Schedule>() {
                        @Override
                        public int compare(Schedule o1, Schedule o2) {
                            return Integer.valueOf(o1.getDay()).compareTo(Integer.valueOf(o2.getDay()));
                        }
                    });
                    schedulesAdapter.setData(scheduleList);

                    rvSchedules.setAdapter(schedulesAdapter);
                }
            }

            @Override
            public void onFailure(Call<SchedulesResponse> call, Throwable t) {

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
    public void onClick(View v) {

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
    public void ClickedSche(Schedule schedule) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        String dia = "";
        String hora = "";
        switch (schedule.getDay()){
            case 1:
                dia = "Lunes";
                break;
            case 2:
                dia = "Martes";
                break;
            case 3:
                dia = "Miércoles";
                break;
            case 4:
                dia = "Jueves";
                break;
            case 5:
                dia = "Viernes";
                break;
            case 6:
                dia = "Sábado";
                break;
            case 7:
                dia = "Domingo";
                break;
        }
        switch (schedule.getHour()){
            case 8:
                hora = "08:00 AM";
                break;
            case 9:
                hora = "09:00 AM";
                break;
            case 10:
                hora = "10:00 AM";
                break;
            case 11:
                hora = "11:00 AM";
                break;
            case 12:
                hora = "12:00 PM";
                break;
            case 13:
                hora = "13:00 PM";
                break;
            case 14:
                hora = "14:00 PM";
                break;
            case 15:
                hora = "15:00 PM";
                break;
            case 16:
                hora = "16:00 PM";
                break;
            case 17:
                hora = "17:00 PM";
                break;
            case 18:
                hora = "18:00 PM";
                break;
            case 19:
                hora = "19:00 PM";
                break;
            case 20:
                hora = "20:00 PM";
                break;
            case 21:
                hora = "21:00 PM";
                break;
            case 22:
                hora = "22:00 PM";
                break;

        }
        Session session2 = new Session();
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String date = df.format(current);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(df.parse(date));
        } catch (java.text.ParseException e){
            e.printStackTrace();
        }
        Log.e("TEST FECHA1", date);
        Log.e("TEST Date", String.valueOf(date2));
        c.add(Calendar.DAY_OF_MONTH, (schedule.getDay()-date2));
        date = df.format(c.getTime());

        Log.e("TEST FECHA", date);


        session2.setDate(date);
        session2.setHour(schedule.getHour());
        session2.setPsychologistDni(psyDNI);
        session2.setPatientDni(passedUser);
        builder1.setMessage("Desea agendar una cita para el día: " + dia + " a las " + hora);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "CONFIRMAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        send(session2);
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
    }

    private void send(Session session) {
        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);
        Call<LoginResponse> setSession = RetrofitClient.getApiSession().setSession(session, token);

        setSession.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getStatus() == 1){
                    Toast.makeText(getApplicationContext(), "Se agendó la cita satisfactoriamente", Toast.LENGTH_LONG);
                    startActivity(new Intent(getApplicationContext(), ConfirmationActivity.class).putExtra("DNI", passedUser).putExtra("PSY", psyDNI).putExtra("Codigo", "horario"));
                } else if(response.body().getStatus() == 0)
                {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }
}
