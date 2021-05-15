package com.example.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.example.mobileapp.Model.Patient;
import com.example.mobileapp.Model.User;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.FileUtils;
import com.example.mobileapp.Utils.ImageFilePath;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.Responses.UserResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    String passedUser, passedPassword;
    EditText etNames, etPhone, etEmail;
    ImageView ivPhoto;
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

        ivPhoto = findViewById(R.id.ivPatientPhoto);




        etNames = findViewById(R.id.etGuardianNames);
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

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            passedUser = intent.getStringExtra("DNI");
            passedPassword = intent.getStringExtra("password");
        }

        Call<UserResponse> userResponse = RetrofitClient.getApiUser().getUser(passedUser);

        userResponse.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getStatus() == 1){
                    fillInformation(response.body());
                    String textNames = fillPatient.getNames();
                    String textLastNames = fillPatient.getLastNames();
                    String textNamesFull = textNames + " " + textLastNames;
                    String textPhone = fillPatient.getPhone();
                    String textEmail = fillPatient.getEmail();
                    etNames.setText(textNamesFull, TextView.BufferType.EDITABLE);
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
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null)
            switch (requestCode){
                case PICK_IMAGE:
                    Uri selectedImage = data.getData();
                    Log.e("TAG 1", "Gets URI ");
                    try {
                        InputStream iStream = getContentResolver().openInputStream(selectedImage);
                        String path = getPath(this, selectedImage);
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "JPEG_" + timeStamp + "_";
                        String realPath = ImageFilePath.getPath(this, data.getData());
                        File file = new File(realPath);
                        file.mkdirs();


                        //selectedImage.mkdirs();
                        File photo = File.createTempFile("Profile pic", ".jpg", file);

                        if(file.exists()){
                            Log.e("TAG", "EXISTE ");
                        } else {
                            Log.e("TAG", "NO EXISTE ");
                        }
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "Profile pic", RequestBody.create(MediaType.parse("image/*"), photo));

                        byte[] fotoBytes = getBytes(iStream);
                        Log.e("TAG 1", "Sets FilePart ");
                        Call<LoginResponse> sendPhoto = RetrofitClient.getApiUser().updatePhoto(passedUser, filePart);

                        sendPhoto.enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                if(response.body().getStatus() == 1){
                                    Toast.makeText(getApplicationContext(), "Foto actualizada correctamente", Toast.LENGTH_LONG).show();
                                    updatePhoto();
                                    Log.e("TAG 1", "Gets Photo ");
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                Log.e("TAG 1", t.getMessage());
                            }
                        });
                    } catch (IOException e) {
                        Log.e("TAG", "Some exception " + e);
                    }
                    break;
            }
    }*/

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
        File file = FileUtils.getFile(this, selectedImage);
        Log.e("PATH", file.getAbsolutePath());


        RequestBody filePart = RequestBody.create(file, MediaType.parse(getContentResolver().getType(selectedImage)));

        Log.e("PATH", filePart.toString());

        MultipartBody.Part filePhoto = MultipartBody.Part.createFormData("photo", file.getName(), filePart);


        Call<LoginResponse> sendPhoto = RetrofitClient.getApiUser().updatePhoto(passedUser, filePhoto);

        sendPhoto.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        updatePhoto();
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

    public String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    private void updatePhoto() {
        //String url = "http://ec2-54-144-123-136.compute-1.amazonaws.com/patient/image/?dni=" + passedUser;
        String url = getString(R.string.baseURL) + "/patient/image/?dni=" + passedUser;

        Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(ivPhoto);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap =
                        MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    private void checkUpdate() {
        user.setDni(passedUser);
        user.setPassword(passedPassword);
        String firstName = "";
        String lastName = "";

        if(etNames.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Ingrese su nombre completo", Toast.LENGTH_SHORT).show();
        }else {
            String fullName = etNames.getText().toString();
            int idx = fullName.lastIndexOf(' ') - 1;
            if (idx == -1)
                throw new IllegalArgumentException("Solo hay un nombre: " + fullName);
            firstName = fullName.substring(0, idx);
            lastName = fullName.substring(idx + 1);
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
            Call<LoginResponse> updatePatient = RetrofitClient.getApiUser().updatePatient(fillPatient);
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
