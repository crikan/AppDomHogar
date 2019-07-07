package com.example.jigokushoujo.appdomhogar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Digits;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


    /*esta clase implementa ValidationListener de la librería saripaar para poder validar el contenido
    de los campos del formulario antes de su envío. Esta librería declara los tipos de contenido
    añadiendo anotaciones declarativas

     una vez validados los datos que guardamos en sus respectivas referencias vamos a proceder
    a conectar con la Base de Datos MySql remota usando un script de php como "web service" en miniatura
    Para ello lo hemos subido al mismo dominio gratuito donde tenemos alojada la web,
    y es el que se encargará de recibir la petición y de conectarse a la base de datos para escribir en ella.
    Para poder mandar los datos desde android hemos optado por usar la librería Volley */

public class ContactActivity extends AppCompatActivity implements Validator.ValidationListener {

    //declaramos las referencias para los elementos del formulario y los tageamos por tipos incluyendo el mensaje
    //al usuario en caso de error en los datos completados
    @NotEmpty(message = "mandatory field")
    private EditText txtName;

    @NotEmpty(message = "mandatory field")
    @Email(message = "please enter email in format: name@example.com")
    private EditText txtEmail;

    @NotEmpty(message = "mandatory field")
    @Digits(integer = 9, message = "Should be 9 digits")
    private EditText txtPhone;
    private String s1, s2, s3, s4, s5;
    Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Objects.requireNonNull(getSupportActionBar()).hide(); //oculta la barra superior

        //vinculamos los campos del formulario con sus referencias
        Button submit = findViewById(R.id.submit_button);
        txtName = findViewById(R.id.name_surname);
        txtEmail = findViewById(R.id.email);
        txtPhone = findViewById(R.id.phone);
        // comprobamos si los checkboxes están marcado y según sea el resultado, transformamos las variables
        //auxiliares que vamos a mandar como parámetros

        //creamos una instancia de nuestro validador de contenido
        validator = new Validator(this);
        validator.setValidationListener(this);

        //al pulsar el botón invocamos al validador
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validamos
                validator.validate();

                //nos conectamos con el script de php y le enviamos los datos ya validados
                ejecutarServicio("https://domhogar.000webhostapp.com/android/androidConnector.php");
            }
        });
    }

    //métodos de obligada implementación propios de ValidationListener. Se pueden usar para informar al usuario
    //del éxito o fracaso de la validación. Para ello se opta por el uso de Toasts
    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "goood", Toast.LENGTH_SHORT).show();
        //además de mostrar el Toast aprovechamos el momento para compbrobar los checkboxes y transformarlos
        //para ser transportados a la Base de Datos
        CheckBox serv1 = findViewById(R.id.serv1);
        s1 = ckbAdapter(serv1);
        CheckBox serv2 = findViewById(R.id.serv2);
        s2 = ckbAdapter(serv2);
        CheckBox serv3 = findViewById(R.id.serv3);
        s3 = ckbAdapter(serv3);
        CheckBox serv4 = findViewById(R.id.serv4);
        s4 = ckbAdapter(serv4);
        CheckBox serv5 = findViewById(R.id.serv5);
        s5 = ckbAdapter(serv5);
    }
    //método que se ejecuta cuando el contenido de los campos no es el adecuado
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, "Please, check again. Personal data is mandatory", Toast.LENGTH_LONG).show();
            }
        }
    }

    //método para usar la condición de los checkboxes
    public String ckbAdapter(CheckBox checkbox){
        if(checkbox.isChecked()){return "1";}
        else {return "0";}
    }

    // método que recibe la url con el php como argumento y se ocupa de conectar con el mismo y enviarle los datos
    private void ejecutarServicio(String URL) {
        //lo primero es crear unos listeners (uno normal y uno de errores que procesarán las respuestas a
        //nuestras peticiones y serán necesarias para construir la propia petición
        Response.Listener<String> listener  = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        };

        // Ahora generamos una petición mediante la creación de un objeto de tipo StringRequest
        //que recibe 4 parámetros en el constructor. (1)tipo de petición (GET o POST), (2)la URL
        //(3)un listener a la espera de respuesta, y (4)un errorlistener a la espera de errores. Los parámetros
        //que llevará la petición van aparte ya que usamos el método POST, de ser GET irían en la URL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, listener, errorListener)
        //parámetros que serán capturados en el script php
        {
            protected Map<String, String> getParams() {
                //necesitaremos un mapa de parámetros ya que estamos usando el método POST.
                Map<String, String> params = new HashMap<String, String>();
                //mediante el método put() añadimos los parámetros en pares de claves, siendo la primera el
                //nombre de la variable que le corresponde en el script de PHP y la segunda, la variable que obtuvimos
                //del formulario y que previamente validamos
                params.put("name", txtName.getText().toString());
                params.put("email", txtEmail.getText().toString());
                params.put("phone", txtPhone.getText().toString());
                params.put("decoWifi", s1);
                params.put("wifiSignal", s2);
                params.put("smartPlugs", s3);
                params.put("smartBulbs", s4);
                params.put("wifiCams", s5);
                return params;
            }
        };
        //para procesar nuestro ojeto stringRequest necesitaremos crear una cola de peticiones y añadirlo a la misma
        RequestQueue cola = Volley.newRequestQueue(ContactActivity.this);
        cola.add(stringRequest);
    }

}

