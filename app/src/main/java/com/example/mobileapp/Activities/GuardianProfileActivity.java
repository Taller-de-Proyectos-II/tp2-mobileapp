package com.example.mobileapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.Model.Guardian;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.FileUtils;
import com.example.mobileapp.Utils.Responses.GuardianResponse;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardianProfileActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener{

    String passedUser, passedPhone, passedEmail;
    GuardianResponse guardianResponse2 = new GuardianResponse();
    ArrayList<Guardian> fillGuardians = new ArrayList<>();
    Guardian utilGuardian = new Guardian();
    AlertDialog alert1;
    EditText etGuardianNames, etGuardianBirthday, etGuardianDNI;
    Guardian guardMock;
    ImageView ivGuardianPhoto;
    public static final int PICK_IMAGE = 0;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_guardian);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Apoderado");

        guardianResponse2.setMessage("");
        guardianResponse2.setStatus(0);
        guardianResponse2.setGuardiansDTO(fillGuardians);

        etGuardianNames = findViewById(R.id.etGuardianNames);
        etGuardianBirthday = findViewById(R.id.etGuardianBirthday);
        etGuardianBirthday.setOnClickListener(this);
        etGuardianDNI = findViewById(R.id.etDNIGuardian);
        ivGuardianPhoto= findViewById(R.id.ivGuardianPhoto);

        Button btnUpdateGuardian = findViewById(R.id.btnAddGuardian);
        btnUpdateGuardian.setOnClickListener(this);
        Button btnDeleteGuardian = findViewById(R.id.btnDeleteGuardian);
        btnDeleteGuardian.setOnClickListener(this);
        Button btnAddPhoto = findViewById(R.id.btnGuardianAddPhoto);
        btnAddPhoto.setOnClickListener(this);




        Intent intent = getIntent();

        if(intent.getExtras() != null){
            guardMock = (Guardian) intent.getSerializableExtra("data");
            String name = guardMock.getNames() + " " + guardMock.getLastNames();
            etGuardianNames.setText(name);
            etGuardianBirthday.setText(guardMock.getBirthday());
            etGuardianDNI.setText(guardMock.getDni());
            passedUser = intent.getStringExtra("DNI");
            passedEmail = intent.getStringExtra("email");
            passedPhone = intent.getStringExtra("phone");
        }

        /*Call<GuardianResponse> guardianResponse = RetrofitClient.getApiGuardian().getGuardian(passedUser);

        guardianResponse.enqueue(new Callback<GuardianResponse>() {
            @Override
            public void onResponse(Call<GuardianResponse> call, Response<GuardianResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getGuardiansDTO() == null) {
                        Toast.makeText(getApplicationContext(), "No tiene apoderados registrados, registre uno para mostrarlo.", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run(){
                                Intent mp = new Intent(getApplicationContext(),MenuActivity.class).putExtra("DNI", passedUser);
                                startActivity(mp);
                            }
                        }, 1000);
                    } else
                    {
                        fillInformation(response.body());
                        String names = fillGuardians.get(0).getNames();
                        String lastNames = fillGuardians.get(0).getLastNames();
                        String fullName = names + " " + lastNames;
                        etGuardianNames.setText(fullName, TextView.BufferType.EDITABLE);
                        etGuardianBirthday.setText(fillGuardians.get(0).getBirthday(), TextView.BufferType.EDITABLE);
                        etGuardianDNI.setText(fillGuardians.get(0).getDni(), TextView.BufferType.EDITABLE);

                    }
                }
                Log.e("AQUIIIIIII", response.body().toString());
            }

            @Override
            public void onFailure(Call<GuardianResponse> call, Throwable t) {
                Log.e("AQUIIIIIII", t.getMessage());
            }
        });*/

        updatePhoto();

        verifyStoragePermissions(this);
    }

    private void updatePhoto() {
        String urlPhoto = getString(R.string.baseURLMock) + "/guardian/image/?dni=" + guardMock.getDni();

        /*Glide.with(this)
                .load(urlPhoto)
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(ivPatientPhoto);*/

        Picasso.with(this).load(urlPhoto).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(ivGuardianPhoto);
    }

    private void fillInformation(GuardianResponse guardianResponse) {
        guardianResponse2.setMessage(guardianResponse.getMessage());
        guardianResponse2.setStatus(guardianResponse.getStatus());
        Guardian utilGuardian = new Guardian();
        utilGuardian.setPhone(guardianResponse.getGuardiansDTO().get(0).getPhone());
        utilGuardian.setNames(guardianResponse.getGuardiansDTO().get(0).getNames());
        utilGuardian.setLastNames(guardianResponse.getGuardiansDTO().get(0).getLastNames());
        utilGuardian.setEmail(guardianResponse.getGuardiansDTO().get(0).getEmail());
        utilGuardian.setBirthday(guardianResponse.getGuardiansDTO().get(0).getBirthday());
        utilGuardian.setDni(guardianResponse.getGuardiansDTO().get(0).getDni());
        utilGuardian.setPatientDni(passedUser);
        utilGuardian.setEmail(passedEmail);
        utilGuardian.setPhone(passedPhone);
        guardianResponse2.getGuardiansDTO().add(utilGuardian);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnAddGuardian:
                checkUpdate();
                break;
            case R.id.etGuardianBirthday:
                Calendar c = Calendar.getInstance();
                DatePickerDialog dpd;
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(GuardianProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        etGuardianBirthday.setText(date);
                    }
                }, day, month, year);
                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();
            case R.id.btnDeleteGuardian:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("¿Desea eliminar a " + guardMock.getNames() + " " + guardMock.getLastNames() + " como su apoderado?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "CONFIRMAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteGuardian();
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
            case R.id.btnGuardianAddPhoto:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, PICK_IMAGE);

                    }
                }, 1000);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            if(data == null){
                Log.e("TAG", "DATA ES NULL ");
                return;
            }
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Uri selectedImage = data.getData();
                uploadPhoto(selectedImage);
            } catch (FileNotFoundException e) {
                Log.e("TAG", "Some exception " + e);
            }
        }
    }


    private void uploadPhoto(Uri selectedImage) {
        File file1 = FileUtils.getFile(this, selectedImage);
        Log.e("PATH", file1.getAbsolutePath());


        RequestBody filePart = RequestBody.create(file1, MediaType.parse(getContentResolver().getType(selectedImage)));

        Log.e("PATH", filePart.toString());

        MultipartBody.Part filePhoto = MultipartBody.Part.createFormData("photo", file1.getName(), filePart);
        MultipartBody file = new MultipartBody.Builder().addFormDataPart("file-type", "profile").addFormDataPart("photo", file1.getName(), filePart).build();

        String url = getString(R.string.baseURLMock) + "guardian/image/?dni=" + guardMock.getDni();
        Call<LoginResponse> sendPhoto = RetrofitClient.getApiGuardian().updatePhoto(url, file);

        sendPhoto.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run(){
                                updatePhoto();
                            }
                        }, 2000);
                    } else {
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                Log.e("TAG", t.getMessage().toString());
            }
        });
    }


    private void deleteGuardian() {
        Call<LoginResponse> deleteGuardian = RetrofitClient.getApiGuardian().deleteGuardian(guardMock.getDni(), passedUser);

        deleteGuardian.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getStatus() == 1){
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            Intent gpa = new Intent(getApplicationContext(), PatientProfileActivity.class).putExtra("DNI", passedUser);
                            startActivity(gpa);
                        }
                    }, 1000);
                } else{
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void checkUpdate() {
        utilGuardian.setPatientDni(passedUser);
        utilGuardian.setPhone(passedPhone);
        utilGuardian.setEmail(passedEmail);

        String fullName = etGuardianNames.getText().toString();
        int idx = fullName.lastIndexOf(' ');
        if(idx == -1)
            throw new IllegalArgumentException("Solo hay un nombre: " + fullName);
        String firstName = fullName.substring(0, idx);
        String lastName = fullName.substring(idx + 1);

        utilGuardian.setNames(firstName);
        utilGuardian.setLastNames(lastName);
        utilGuardian.setBirthday(etGuardianBirthday.getText().toString());
        utilGuardian.setDni(etGuardianDNI.getText().toString());

        Call<LoginResponse> updateGuardian = RetrofitClient.getApiGuardian().updateGuardian(utilGuardian);
        updateGuardian.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getStatus() == 1)
                {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            Intent gpa = new Intent(getApplicationContext(), GuardianListActivty.class).putExtra("DNI", passedUser).
                                    putExtra("email", passedEmail).
                                    putExtra("phone", passedPhone);
                            startActivity(gpa);
                        }
                    }, 1000);
                }
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("AQUIIIIIII", t.getMessage());
            }
        });

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.itAlertas:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ma = new Intent(getApplicationContext(),ManifestationsActivity.class).putExtra("DNI", passedUser);
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
}
