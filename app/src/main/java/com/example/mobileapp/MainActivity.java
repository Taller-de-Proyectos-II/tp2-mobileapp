package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.example.mobileapp.Controller.ILoginController;
import com.example.mobileapp.Model.User;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUser;
    EditText etPassword;
    String user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(this);

        Button buttonRegister = findViewById(R.id.btnRegistrar);
        buttonRegister.setOnClickListener(this);

        etUser = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etContraseña);

        /*btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(TextUtils.isEmpty(etUser.getText().toString())||TextUtils.isEmpty(etPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Debe ingresar sus credenciales",Toast.LENGTH_LONG);
                }else {
                    checkLogin();
                }
            }
        });*/
    }

    private void checkLogin() {

        user = etUser.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(user,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Login satisfactorio",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("TAG",t.toString());
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                checkLogin();
                //Toast.makeText(getApplicationContext(), "Login satisfactorio",Toast.LENGTH_LONG).show();
                break;
            case R.id.btnRegistrar:
                Intent fp = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(fp);
                break;
        }

    }
}
