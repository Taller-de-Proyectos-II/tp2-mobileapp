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
    CheckBox cbPrivacy, cbConsent;

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

        TextView tvConditions2 = findViewById(R.id.tvConditions2);

        cbPrivacy = findViewById(R.id.cbTerminos);

        cbConsent = findViewById(R.id.cbTerminos2);


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

        String consentText = "DOCUMENTO DE CONSENTIMIENTO INFORMADO\n" +
                "\n" +
                "Título del trabajo de investigación:\n" +
                "Sistema de monitoreo para identificar el comportamiento de la salud mental en poblaciones vulnerables.\n" +
                "Nombres de los investigadores:\n" +
                "Roger Fernando López Trujillo – Nathaly Mercedes Gamarra Santos\n" +
                "\n" +
                "1.\tIntroducción\n" +
                "A usted se le está invitando a participar de este proyecto de investigación el cual busca: Implementar una solución tecnológica para el monitoreo del comportamiento de trastorno de ansiedad en poblaciones vulnerables.\n" +
                "En el presente documento de consentimiento informado usted encontrará información importante relacionada a: la finalidad del estudio, lo que se le pedirá a usted que haga, los riesgos y/o beneficios de su participación, entre otros aspectos que le permitirán decidir si participa o no. Lea detenidamente este documento y siéntase usted con la libertad de hacer las preguntas que considere necesarias.\n" +
                "Si usted decide participar de esta investigación, deberá colocar su nombre y firmar este documento; se le brindará una copia firmada y fechada.\n" +
                "1.\tJustificación del estudio\n" +
                "\n" +
                "Por medio de este estudio, se busca poder procesar sus datos brindados en la aplicación y determinar parámetros para diagnosticar la ansiedad, como en qué nivel se encuentra y así poder brindarle una atención y el monitoreo continuo de sus síntomas con ayuda del psicólogo.\n" +
                "\n" +
                "2.\tProcedimientos del estudio\n" +
                "\n" +
                "El procedimiento de este estudio es aceptar el uso de sus datos personales, como nombre, documento de identidad, fecha de nacimiento y datos necesarios al proyecto. Al aceptar, nos ayudará en poder responder cuestionarios involucrados con temas de ansiedad y depresión. Además, al hacer uso de nuestro aplicativo, poder responder encuestas y preguntas acerca de mejoras para sí mismo.\n" +
                "\n" +
                "3.\tRiesgos\n" +
                "\n" +
                "Dentro del proyecto de investigación, se define a riesgos como poder brindar un mal diagnóstico por medio de la aplicación y que este no esté de acuerdo con el psicólogo asignado. Además, la atención en citas virtuales tome más tiempo para la reserva de estas debido a no tener horarios disponibles o poca información del psicólogo para la atención al paciente. \n" +
                "\n" +
                "4.\tBeneficios\n" +
                "\n" +
                "Con el estudio, se puede brindar diagnósticos más acertados por medio de las respuestas que nos brinde con ayuda de los aplicativos. Con los datos obtenidos se puede brindar citas virtuales con un psicólogo y poder darle una consulta, en el cual reafirmaría el diagnostico obtenido de manera automática. Además, con ayuda de los datos brindados se podrá brindar un monitoreo continuo con respuestas e informes directo de los psicólogos.\n" +
                "\n" +
                "5.\tConfidencialidad de la información\n" +
                "\n" +
                "Se garantiza la confidencialidad de los datos solo para el uso del proyecto.\n" +
                "\n" +
                "6.\tContacto en caso de consultas o comentarios\n" +
                "Contacto con los investigadores\n" +
                "Para comunicarse con los investigadores de este estudio, podrá hacerlo con Roger Fernando López Trujillo, u201210776@upc.edu.pe o al teléfono 958526300 y Nathaly Mercedes Gamarra Santos, a través del correo electrónico u201416422@upc.edu.pe o al teléfono 949713293.\n" +
                "Contacto con el asesor\n" +
                "\n" +
                "Para contactar el asesor de este estudio Jimmy Armas Aguirre puede escribir al correo electrónico jimmy.armas@upc.pe\n" +
                "Comité de ética\n" +
                "\n" +
                "Si usted tiene alguna duda sobre el estudio o siente que sus derechos fueron vulnerados, puede contactar a la Presidente del Comité de Ética en Investigación de la Universidad Peruana de Ciencias Aplicadas, Mag. Ilce Casanova Olortegui al teléfono 313-3333, anexo 2702 o al correo electrónico PCNUSCAS@upc.edu.pe\n" +
                "El subcomité de ética está formado por personas externas al proyecto de investigación, cuya función es velar que se respete la dignidad y derecho de los participantes, según el diseño y desarrollo de la investigación.\n" +
                "Derecho a retirarse\n" +
                "\n" +
                "Usted podrá retirarse en cualquier momento del estudio sin ninguna explicación al respecto.\n" +
                "\n" +
                "\n" +
                "En el caso de menores de 18 años o de participantes que tengan alguna limitación mental que los incapacite para firmar el consentimiento informado, se reconocerá como su representante al padre, la madre o algún otro familiar o apoderado. Los analfabetos podrán utilizar su huella digital (dedo índice) en lugar de la firma. Una copia del documento de consentimiento informado siempre debe ser entregado al firmante.\n" +
                "Los menores de edad (de 10 a 18 años) además deberán dar su asentimiento de participación en la investigación. Si se niegan no podrá realizarse la investigación en ellos, así su representante legal esté de acuerdo con firmar el documento de consentimiento informado.\n";

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


        AlertDialog dialog2 = new AlertDialog.Builder(this)
                .setTitle("Consentimiento")
                .setMessage(consentText)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        cbConsent.setChecked(true);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
        TextView textView2 = (TextView) dialog2.findViewById(android.R.id.message);
        textView2.setScroller(new Scroller(this));
        textView2.setVerticalScrollBarEnabled(true);
        textView2.setMovementMethod(new ScrollingMovementMethod());


        dialog2.dismiss();

        String text = "He leído y acepto las políticas de uso de datos personales";
        String text2 = "He leído y acepto el consentimiento informado";

        SpannableString ss = new SpannableString(text);

        SpannableString ss2 = new SpannableString(text2);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                dialog1.show();
            }
        };
        ss.setSpan(clickableSpan1, 22, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                dialog2.show();
            }
        };
        ss2.setSpan(clickableSpan2, 21, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvConditions.setText(ss);
        tvConditions.setMovementMethod(LinkMovementMethod.getInstance());

        tvConditions2.setText(ss2);
        tvConditions2.setMovementMethod(LinkMovementMethod.getInstance());
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
                if(cbPrivacy.isChecked() && cbConsent.isChecked()) {
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
                } else{
                    Toast.makeText(getApplicationContext(),"Debe aceptar las políticas de privacidad y el consentimiento", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Complete todos los campos correctamente", Toast.LENGTH_SHORT).show();
            }



    }
}
