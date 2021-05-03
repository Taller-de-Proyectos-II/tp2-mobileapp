package com.example.mobileapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.Model.Patient;
import com.example.mobileapp.Model.User;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.LoginResponse;
import com.example.mobileapp.Utils.RetrofitClient;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etBirthday, etEmail, etLastNames, etNames, etPhone, etDni, etPassword;
    CheckBox cbPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnRegistro = findViewById(R.id.btnRegistrar);
        btnRegistro.setOnClickListener(this);

        etBirthday = findViewById(R.id.etFechaNacimiento);
        etBirthday.setOnClickListener(this);
        etEmail = findViewById(R.id.etCorreo);
        etLastNames = findViewById(R.id.etApellidos);
        etNames = findViewById(R.id.etNombres);
        etPhone = findViewById(R.id.etTelefono);
        etDni = findViewById(R.id.etDNI);
        etPassword = findViewById(R.id.etContraseña);

        TextView tvConditions = findViewById(R.id.tvConditions);


        cbPrivacy = findViewById(R.id.cbTerminos);


        String privacyText = "POLÍTICA DE PRIVACIDAD \n" +
                "El presente Política de Privacidad establece los términos en que  PhychoMonitoringApp usa y protege la información que es proporcionada por sus usuarios al momento de utilizar su sitio web. Esta compañía está comprometida con la seguridad de los datos de sus usuarios. Cuando le pedimos llenar los campos de información personal con la cual usted pueda ser identificado, lo hacemos asegurando que sólo se empleará de acuerdo con los términos de este documento. Sin embargo, esta Política de Privacidad puede cambiar con el tiempo o ser actualizada por lo que le recomendamos y enfatizamos revisar continuamente esta página para asegurarse que está de acuerdo con dichos cambios. \n" +
                "Información que es recogida \n" +
                "Nuestro sitio web podrá recoger información personal, por ejemplo: Nombre, información de contacto como su dirección de correo electrónica e información demográfica. Así mismo cuando sea necesario podrá ser requerida información específica para procesar algún pedido o realizar una entrega o facturación. \n" +
                "Uso de la información recogida \n" +
                "Nuestro sitio web emplea la información con el fin de proporcionar el mejor servicio posible, particularmente para mantener un registro de usuarios, de pedidos en caso de que aplique, y mejorar nuestros productos y servicios.  Es posible que sean enviados correos electrónicos periódicamente a través de nuestro sitio con ofertas especiales, nuevos productos y otra información publicitaria que consideremos relevante para usted o que pueda brindarle algún beneficio, estos correos electrónicos serán enviados a la dirección que usted proporcione y podrán ser cancelados en cualquier momento. \n" +
                "PhychoMonitoringApp está altamente comprometido para cumplir con el compromiso de mantener su información segura. Usamos los sistemas más avanzados y los actualizamos constantemente para asegurarnos que no exista ningún acceso no autorizado. \n" +
                "Cookies \n" +
                "Una cookie se refiere a un fichero que es enviado con la finalidad de solicitar permiso para almacenarse en su ordenador, al aceptar dicho fichero se crea y la cookie sirve entonces para tener información respecto al tráfico web, y también facilita las futuras visitas a una web recurrente. Otra función que tienen las cookies es que con ellas las webs pueden reconocerte individualmente y por tanto brindarte el mejor servicio personalizado de su web. \n" +
                "Nuestro sitio web emplea las cookies para poder identificar las páginas que son visitadas y su frecuencia. Esta información es empleada únicamente para análisis estadístico y después la información se elimina de forma permanente. Usted puede eliminar las cookies en cualquier momento desde su ordenador. Sin embargo, las cookies ayudan a proporcionar un mejor servicio de los sitios web, estás no dan acceso a información de su ordenador ni de usted, a menos de que usted así lo quiera y la proporcione directamente. Usted puede aceptar o negar el uso de cookies, sin embargo, la mayoría de los navegadores aceptan cookies automáticamente pues sirve para tener un mejor servicio web. También usted puede cambiar la configuración de su ordenador para declinar las cookies. Si se declinan es posible que no pueda utilizar algunos de nuestros servicios. \n" +
                "Enlaces a Terceros \n" +
                "Este sitio web pudiera contener en laces a otros sitios que pudieran ser de su interés. Una vez que usted de clic en estos enlaces y abandone nuestra página, ya no tenemos control sobre al sitio al que es redirigido y por lo tanto no somos responsables de los términos o privacidad ni de la protección de sus datos en esos otros sitios terceros. Dichos sitios están sujetos a sus propias políticas de privacidad por lo cual es recomendable que los consulte para confirmar que usted está de acuerdo con estas. \n" +
                "Control de su información personal \n" +
                "En cualquier momento usted puede restringir la recopilación o el uso de la información personal que es proporcionada a nuestro sitio web.  Cada vez que se le solicite rellenar un formulario, como el de alta de usuario, puede marcar o desmarcar la opción de recibir información por correo electrónico.  En caso de que haya marcado la opción de recibir nuestro boletín o publicidad usted puede cancelarla en cualquier momento. \n" +
                "Esta compañía no venderá, cederá ni distribuirá la información personal que es recopilada sin su consentimiento, salvo que sea requerido por un juez con un orden judicial. \n" +
                "PhychoMonitoringApp  se reserva el derecho de cambiar los términos de la presente Política de Privacidad en cualquier momento. \n";

        AlertDialog dialog1 = new AlertDialog.Builder(this)
                .setTitle("Políticas de privacidad")
                .setMessage(privacyText)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        cbPrivacy.setChecked(true);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
        TextView textView = (TextView) dialog1.findViewById(android.R.id.message);
        textView.setScroller(new Scroller(this));
        textView.setVerticalScrollBarEnabled(true);
        textView.setMovementMethod(new ScrollingMovementMethod());

        dialog1.dismiss();

        String text = "He leído y acepto las políticas de uso de datos personales";

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                dialog1.show();
            }
        };
        ss.setSpan(clickableSpan1, 22, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        tvConditions.setText(ss);
        tvConditions.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistrar:
                checkRegistro();
                //Toast.makeText(getApplicationContext(), "Login satisfactorio",Toast.LENGTH_LONG).show();
                break;
            case R.id.etFechaNacimiento:
                Calendar c = Calendar.getInstance();
                DatePickerDialog dpd;
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        etBirthday.setText(date);
                    }
                }, day, month, year);
                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();
                break;
        }
    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private void checkRegistro() {
        Patient patient = new Patient();
        String birthday, email, lastNames, names, phone, dni, password, password2;

        User userLoginDTO = new User();

        birthday = etBirthday.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        lastNames = etLastNames.getText().toString().trim();
        names = etNames.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        dni = etDni.getText().toString().trim();
        password = etPassword.getText().toString().trim();


        userLoginDTO.setDni(dni);
        userLoginDTO.setPassword(password);


        patient.setBirthday(birthday);
        patient.setDescription("Descripcion");
        patient.setEmail(email);

        patient.setLastNames(lastNames);
        patient.setNames(names);
        patient.setPhone(phone);


        patient.setUser(userLoginDTO);


            if(etBirthday.getText().toString().trim().length() > 0 && etEmail.getText().toString().trim().length() > 0 && etLastNames.getText().toString().trim().length() > 0
            && etNames.getText().toString().trim().length() > 0 && etPhone.getText().toString().trim().length() > 0 && etDni.getText().toString().trim().length() > 0
                    && etPassword.getText().toString().trim().length() > 0 && isValidEmailId(etEmail.getText().toString().trim())) {
                Call<LoginResponse> call = RetrofitClient.getApiLogin().register(patient);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body().getStatus() == 1) {
                            String message = response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }, 1000);
                        } else {
                            String message = response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        String message = t.getMessage();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Complete todos los campos correctamente", Toast.LENGTH_SHORT).show();
            }



    }
}
