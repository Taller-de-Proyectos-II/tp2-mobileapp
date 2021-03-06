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
        etPassword = findViewById(R.id.etContrase??a);

        TextView tvConditions = findViewById(R.id.tvConditions);

        TextView tvConditions2 = findViewById(R.id.tvConditions2);

        cbPrivacy = findViewById(R.id.cbTerminos);

        cbConsent = findViewById(R.id.cbTerminos2);


        String privacyText = "POL??TICA DE PRIVACIDAD \n" +
                "El presente Pol??tica de Privacidad establece los t??rminos en que??? PhychoMonitoringApp usa y protege la informaci??n que es proporcionada por sus usuarios al momento de utilizar su sitio web. Esta compa????a est?? comprometida con la seguridad de los datos de sus usuarios. Cuando le pedimos llenar los campos de informaci??n personal con la cual usted pueda ser identificado, lo hacemos asegurando que s??lo se emplear?? de acuerdo con los t??rminos de este documento. Sin embargo, esta Pol??tica de Privacidad puede cambiar con el tiempo o ser actualizada por lo que le recomendamos y enfatizamos revisar continuamente esta p??gina para asegurarse que est?? de acuerdo con dichos cambios. \n" +
                "Informaci??n que es recogida \n" +
                "Nuestro sitio web podr?? recoger informaci??n personal, por ejemplo: Nombre,???informaci??n de contacto como???su direcci??n de correo electr??nica e informaci??n demogr??fica. As?? mismo cuando sea necesario podr?? ser requerida informaci??n espec??fica para procesar alg??n pedido o realizar una entrega o facturaci??n. \n" +
                "Uso de la informaci??n recogida \n" +
                "Nuestro sitio web emplea la informaci??n con el fin de proporcionar el mejor servicio posible, particularmente para mantener un registro de usuarios, de pedidos en caso de que aplique, y mejorar nuestros productos y servicios. ???Es posible que sean enviados correos electr??nicos peri??dicamente a trav??s de nuestro sitio con ofertas especiales, nuevos productos y otra informaci??n publicitaria que consideremos relevante para usted o que pueda brindarle alg??n beneficio, estos correos electr??nicos ser??n enviados a la direcci??n que usted proporcione y podr??n ser cancelados en cualquier momento. \n" +
                "PhychoMonitoringApp est?? altamente comprometido para cumplir con el compromiso de mantener su informaci??n segura. Usamos los sistemas m??s avanzados y los actualizamos constantemente para asegurarnos que no exista ning??n acceso no autorizado. \n" +
                "Cookies \n" +
                "Una cookie se refiere a un fichero que es enviado con la finalidad de solicitar permiso para almacenarse en su ordenador, al aceptar dicho fichero se crea y la cookie sirve entonces para tener informaci??n respecto al tr??fico web, y tambi??n facilita las futuras visitas a una web recurrente. Otra funci??n que tienen las cookies es que con ellas las webs pueden reconocerte individualmente y por tanto brindarte el mejor servicio personalizado de su web. \n" +
                "Nuestro sitio web emplea las cookies para poder identificar las p??ginas que son visitadas y su frecuencia. Esta informaci??n es empleada ??nicamente para an??lisis estad??stico y despu??s la informaci??n se elimina de forma permanente. Usted puede eliminar las cookies en cualquier momento desde su ordenador. Sin embargo, las cookies ayudan a proporcionar un mejor servicio de los sitios web, est??s no dan acceso a informaci??n de su ordenador ni de usted, a menos de que usted as?? lo quiera y la proporcione directamente. Usted puede aceptar o negar el uso de cookies, sin embargo, la mayor??a de los navegadores aceptan cookies autom??ticamente pues sirve para tener un mejor servicio web. Tambi??n usted puede cambiar la configuraci??n de su ordenador para declinar las cookies. Si se declinan es posible que no pueda utilizar algunos de nuestros servicios. \n" +
                "Enlaces a Terceros \n" +
                "Este sitio web pudiera contener en laces a otros sitios que pudieran ser de su inter??s. Una vez que usted de clic en estos enlaces y abandone nuestra p??gina, ya no tenemos control sobre al sitio al que es redirigido y por lo tanto no somos responsables de los???t??rminos o privacidad???ni de la protecci??n de sus datos en esos otros sitios terceros. Dichos sitios est??n sujetos a sus propias pol??ticas de privacidad por lo cual es recomendable que los consulte para confirmar que usted est?? de acuerdo con estas. \n" +
                "Control de su informaci??n personal \n" +
                "En cualquier momento usted puede restringir la recopilaci??n o el uso de la informaci??n personal que es proporcionada a nuestro sitio web.??? Cada vez que se le solicite rellenar un formulario, como el de alta de usuario, puede marcar o desmarcar la opci??n de recibir informaci??n por correo electr??nico. ???En caso de que haya marcado la opci??n de recibir nuestro bolet??n o publicidad usted puede cancelarla en cualquier momento. \n" +
                "Esta compa????a no vender??, ceder?? ni distribuir?? la informaci??n personal que es recopilada sin su consentimiento, salvo que sea requerido por un juez con un orden judicial. \n" +
                "PhychoMonitoringApp ???se reserva el derecho de cambiar los t??rminos de la presente Pol??tica de Privacidad en cualquier momento. \n";

        String consentText = "DOCUMENTO DE CONSENTIMIENTO INFORMADO\n" +
                "\n" +
                "T??tulo del trabajo de investigaci??n:\n" +
                "Sistema de monitoreo para identificar el comportamiento de la salud mental en poblaciones vulnerables.\n" +
                "Nombres de los investigadores:\n" +
                "Roger Fernando L??pez Trujillo ??? Nathaly Mercedes Gamarra Santos\n" +
                "\n" +
                "1.\tIntroducci??n\n" +
                "A usted se le est?? invitando a participar de este proyecto de investigaci??n el cual busca: Implementar una soluci??n tecnol??gica para el monitoreo del comportamiento de trastorno de ansiedad en poblaciones vulnerables.\n" +
                "En el presente documento de consentimiento informado usted encontrar?? informaci??n importante relacionada a: la finalidad del estudio, lo que se le pedir?? a usted que haga, los riesgos y/o beneficios de su participaci??n, entre otros aspectos que le permitir??n decidir si participa o no. Lea detenidamente este documento y si??ntase usted con la libertad de hacer las preguntas que considere necesarias.\n" +
                "Si usted decide participar de esta investigaci??n, deber?? colocar su nombre y firmar este documento; se le brindar?? una copia firmada y fechada.\n" +
                "1.\tJustificaci??n del estudio\n" +
                "\n" +
                "Por medio de este estudio, se busca poder procesar sus datos brindados en la aplicaci??n y determinar par??metros para diagnosticar la ansiedad, como en qu?? nivel se encuentra y as?? poder brindarle una atenci??n y el monitoreo continuo de sus s??ntomas con ayuda del psic??logo.\n" +
                "\n" +
                "2.\tProcedimientos del estudio\n" +
                "\n" +
                "El procedimiento de este estudio es aceptar el uso de sus datos personales, como nombre, documento de identidad, fecha de nacimiento y datos necesarios al proyecto. Al aceptar, nos ayudar?? en poder responder cuestionarios involucrados con temas de ansiedad y depresi??n. Adem??s, al hacer uso de nuestro aplicativo, poder responder encuestas y preguntas acerca de mejoras para s?? mismo.\n" +
                "\n" +
                "3.\tRiesgos\n" +
                "\n" +
                "Dentro del proyecto de investigaci??n, se define a riesgos como poder brindar un mal diagn??stico por medio de la aplicaci??n y que este no est?? de acuerdo con el psic??logo asignado. Adem??s, la atenci??n en citas virtuales tome m??s tiempo para la reserva de estas debido a no tener horarios disponibles o poca informaci??n del psic??logo para la atenci??n al paciente. \n" +
                "\n" +
                "4.\tBeneficios\n" +
                "\n" +
                "Con el estudio, se puede brindar diagn??sticos m??s acertados por medio de las respuestas que nos brinde con ayuda de los aplicativos. Con los datos obtenidos se puede brindar citas virtuales con un psic??logo y poder darle una consulta, en el cual reafirmar??a el diagnostico obtenido de manera autom??tica. Adem??s, con ayuda de los datos brindados se podr?? brindar un monitoreo continuo con respuestas e informes directo de los psic??logos.\n" +
                "\n" +
                "5.\tConfidencialidad de la informaci??n\n" +
                "\n" +
                "Se garantiza la confidencialidad de los datos solo para el uso del proyecto.\n" +
                "\n" +
                "6.\tContacto en caso de consultas o comentarios\n" +
                "Contacto con los investigadores\n" +
                "Para comunicarse con los investigadores de este estudio, podr?? hacerlo con Roger Fernando L??pez Trujillo, u201210776@upc.edu.pe o al tel??fono 958526300 y Nathaly Mercedes Gamarra Santos, a trav??s del correo electr??nico u201416422@upc.edu.pe o al tel??fono 949713293.\n" +
                "Contacto con el asesor\n" +
                "\n" +
                "Para contactar el asesor de este estudio Jimmy Armas Aguirre puede escribir al correo electr??nico jimmy.armas@upc.pe\n" +
                "Comit?? de ??tica\n" +
                "\n" +
                "Si usted tiene alguna duda sobre el estudio o siente que sus derechos fueron vulnerados, puede contactar a la Presidente del Comit?? de ??tica en Investigaci??n de la Universidad Peruana de Ciencias Aplicadas, Mag. Ilce Casanova Olortegui al tel??fono 313-3333, anexo 2702 o al correo electr??nico PCNUSCAS@upc.edu.pe\n" +
                "El subcomit?? de ??tica est?? formado por personas externas al proyecto de investigaci??n, cuya funci??n es velar que se respete la dignidad y derecho de los participantes, seg??n el dise??o y desarrollo de la investigaci??n.\n" +
                "Derecho a retirarse\n" +
                "\n" +
                "Usted podr?? retirarse en cualquier momento del estudio sin ninguna explicaci??n al respecto.\n" +
                "\n" +
                "\n" +
                "En el caso de menores de 18 a??os o de participantes que tengan alguna limitaci??n mental que los incapacite para firmar el consentimiento informado, se reconocer?? como su representante al padre, la madre o alg??n otro familiar o apoderado. Los analfabetos podr??n utilizar su huella digital (dedo ??ndice) en lugar de la firma. Una copia del documento de consentimiento informado siempre debe ser entregado al firmante.\n" +
                "Los menores de edad (de 10 a 18 a??os) adem??s deber??n dar su asentimiento de participaci??n en la investigaci??n. Si se niegan no podr?? realizarse la investigaci??n en ellos, as?? su representante legal est?? de acuerdo con firmar el documento de consentimiento informado.\n";

        AlertDialog dialog1 = new AlertDialog.Builder(this)
                .setTitle("Pol??ticas de privacidad")
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

        String text = "He le??do y acepto las pol??ticas de uso de datos personales";
        String text2 = "He le??do y acepto el consentimiento informado";

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
                    Toast.makeText(getApplicationContext(),"Debe aceptar las pol??ticas de privacidad y el consentimiento", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Complete todos los campos correctamente", Toast.LENGTH_SHORT).show();
            }



    }
}
