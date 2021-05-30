package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.mobileapp.Model.Patient;
import com.example.mobileapp.Model.User;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.FileUtils;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.Responses.UserResponse;
import com.example.mobileapp.Utils.RetrofitClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    String passedUser, passedPassword;
    EditText etNames, etLastNames, etPhone, etEmail;
    ImageView ivPatientPhoto;
    UserResponse fillUser = new UserResponse();
    Patient fillPatient = new Patient();




    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int PICK_IMAGE = 0;
    User user = new User();
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        getSupportActionBar().setTitle("Mi Perfil");

        fillUser.setMessage("");
        fillUser.setStatus(0);
        fillUser.setPatient(fillPatient);

        ivPatientPhoto = findViewById(R.id.ivPatientPhoto);

        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);


        etNames = findViewById(R.id.etGuardianNames);
        etLastNames = findViewById(R.id.etGuardianLastNames);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);

        Button btnGuardian = findViewById(R.id.btnGuardian);
        btnGuardian.setOnClickListener(this);
        Button btnUpdatePatient = findViewById(R.id.btnUpdatePatient);
        btnUpdatePatient.setOnClickListener(this);
        Button btnRegisterGuardian = findViewById(R.id.btnRegisterGuardian);
        btnRegisterGuardian.setOnClickListener(this);
        Button btnAddPhoto = findViewById(R.id.btnAddPhoto);
        btnAddPhoto.setOnClickListener(this);
        Button btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(this);


        Intent intent = getIntent();

        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
            passedPassword = intent.getStringExtra("password");
        }

        Call<UserResponse> userResponse = RetrofitClient.getApiUser().getUser(passedUser, token);

        userResponse.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getStatus() == 1){
                    fillInformation(response.body());
                    String textPhone = fillPatient.getPhone();
                    String textEmail = fillPatient.getEmail();
                    etNames.setText(fillPatient.getNames(), TextView.BufferType.EDITABLE);
                    etLastNames.setText(fillPatient.getLastNames(), TextView.BufferType.EDITABLE);
                    etPhone.setText(textPhone, TextView.BufferType.EDITABLE);
                    etEmail.setText(textEmail, TextView.BufferType.EDITABLE);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });

        updatePhoto();

        verifyStoragePermissions(this);

    }

    private void fillInformation(UserResponse userResponse) {
        fillUser.setMessage(userResponse.getMessage());
        fillUser.setStatus(userResponse.getStatus());
        fillPatient.setPhone(userResponse.getPatient().getPhone());
        fillPatient.setNames(userResponse.getPatient().getNames());
        fillPatient.setLastNames(userResponse.getPatient().getLastNames());
        fillPatient.setEmail(userResponse.getPatient().getEmail());
        fillPatient.setBirthday(userResponse.getPatient().getBirthday());
        fillPatient.setDescription(userResponse.getPatient().getDescription());
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

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnGuardian:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent gpa = new Intent(getApplicationContext(), GuardianListActivty.class).putExtra("DNI", passedUser).
                                                                                                        putExtra("email", fillPatient.getEmail()).
                                                                                                        putExtra("phone", fillPatient.getPhone());
                        startActivity(gpa);
                    }
                }, 1000);
                break;
            case R.id.btnRegisterGuardian:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent gpa2 = new Intent(getApplicationContext(), GuardianAddActivity.class).putExtra("DNI", passedUser).
                                                                                                        putExtra("email", fillPatient.getEmail()).
                                                                                                        putExtra("password", passedPassword).
                                                                                                        putExtra("phone", fillPatient.getPhone());
                        startActivity(gpa2);
                    }
                }, 1000);
                break;
            case R.id.btnAddPhoto:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, PICK_IMAGE);

                    }
                }, 1000);
                break;
            case R.id.btnUpdatePatient:
                checkUpdate();
                break;
            case R.id.btnChangePassword:
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent ma = new Intent(getApplicationContext(),ChangePwActivity.class).putExtra("DNI", passedUser).putExtra("password", passedPassword);
                        startActivity(ma);
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
        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);
        File file1 = FileUtils.getFile(this, selectedImage);
        Log.e("PATH", file1.getAbsolutePath());


        RequestBody filePart = RequestBody.create(file1, MediaType.parse(getContentResolver().getType(selectedImage)));

        Log.e("PATH", filePart.toString());

        MultipartBody.Part filePhoto = MultipartBody.Part.createFormData("photo", file1.getName(), filePart);
        MultipartBody file = new MultipartBody.Builder().addFormDataPart("file-type", "profile").addFormDataPart("photo", file1.getName(), filePart).build();

        String url = getString(R.string.baseURLMock) + "patient/image/?dni=" + passedUser;
        Call<LoginResponse> sendPhoto = RetrofitClient.getApiUser().updatePhoto(url, file, token);

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


    private void updatePhoto() {
        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);

        /*OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        final OkHttpClient client2 = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, okhttp3.Response response) throws IOException {
                        return response.request().newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build();
                    }
                })
                .build();
        */
        String urlPhoto = getString(R.string.baseURLMock) + "/patient/image/?dni=" + passedUser;


        GlideUrl glideUrl = new GlideUrl(urlPhoto,
                new LazyHeaders.Builder()
                    .addHeader("Authorization",token)
                    .build());
        Log.e("TOKEN", token);
        Log.e("URL", glideUrl.toString());

        Glide.with(this)
                .load(glideUrl)
                .apply(new RequestOptions().override(600,600))
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ivPatientPhoto);


        /*Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(client2))
                .build();
        Picasso.setSingletonInstance(picasso);
        picasso.load(urlPhoto).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(ivPatientPhoto);
         */
    }


    private void checkUpdate() {
        SharedPreferences preferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", null);
        user.setDni(passedUser);
        user.setPassword(passedPassword);
        String firstName = "";
        String lastName = "";

        if(etNames.getText().toString().trim().length() == 0 || etLastNames.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Ingrese su nombre completo", Toast.LENGTH_SHORT).show();
        }else {
            firstName = etNames.getText().toString().trim();
            lastName = etLastNames.getText().toString().trim();
        }

        if(etPhone.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Ingrese su número telefónico", Toast.LENGTH_SHORT).show();
        }

        if(etEmail.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
        }

        fillPatient.setPhone(etPhone.getText().toString());
        fillPatient.setNames(firstName);
        fillPatient.setLastNames(lastName);
        fillPatient.setEmail(etEmail.getText().toString());
        fillPatient.setUser(user);
        if(etNames.getText().toString().trim().length() > 0 && etPhone.getText().toString().trim().length() > 0 && etEmail.getText().toString().trim().length() > 0) {
            Call<LoginResponse> updatePatient = RetrofitClient.getApiUser().updatePatient(fillPatient, token);
            updatePatient.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        } else
        {

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
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
}
