package com.example.chatconversa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Context;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button iniciar;
    private TextInputEditText user;
    private TextInputEditText password;
    private   String device_id;
    public static String token;
    public static String fotoperfil;
    public static String username;
    public static int user_id;

    public static final String REJEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,12}$";

    private ServicioWeb servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        iniciar = findViewById(R.id.iniciar);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        device_id = generateUUID();
        iniciar.setOnClickListener(this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        servicio = retrofit.create(ServicioWeb.class);
    }

    @Override
    public void onClick(View v) {
        if(validar()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this);
            alert.setMessage("La sesion se encuentra iniciada en otro dispositivo, porfavor cierre sesion antes de iniciar");
            alert.setTitle("Login");
            alert2.setMessage("Datos de usuario no existentes, porfavor registrese");
            alert2.setTitle("Login");
            Login log = new Login(user.getText().toString(), password.getText().toString(), device_id);
            System.out.println("DEVICE_ID: " + log.getDevice_id());
            Call<RespuestaWS> resp = servicio.login(log);
            Constantes constantes = new Constantes();
            resp.enqueue(new Callback<RespuestaWS>() {
                @Override
                public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {
                    if (response.isSuccessful() && response != null && response.body() != null) {
                        RespuestaWS respuesta = response.body();
                        if (respuesta.getStatus_code() == 200) {
                            constantes.setToken(respuesta.getToken());
                            Log.d("Retrofit:", "Inicio de sesion exitoso");
                            System.out.println(respuesta.getToken());

                            //-------------------AQUI OBTIENES DATOS PARA ENVIAR MSG---------------------------//

                            token = respuesta.getToken();
                            user_id = respuesta.getData().getId();
                            username = respuesta.getData().getUsername();
                            fotoperfil = respuesta.getData().getImage();
                            //Log.d("Retrofit", "Token: " + respuesta.getToken());
                            gotoLogout(token, user_id, username,fotoperfil);


                            ///-----------------------------------------------------------///
                        }
                    } else {
                        Gson gson = new Gson();
                        ErrorResponse respuestaerror = new ErrorResponse();
                        try {
                            respuestaerror = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                            System.out.println("Response error: " + respuestaerror);
                            if (respuestaerror.getStatus_code() == 400) {
                                Log.d("Retrofit", "ERROR: " + respuestaerror.getMessage());
                                alert.show();
                            } else {
                                if (respuestaerror.getStatus_code() == 401 && respuestaerror.getMessage()
                                        .equals("Las credenciales son incorrectas o no existen")) {
                                    Log.d("Retrofit", "ERROR: " + respuestaerror.getMessage());
                                    alert2.show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespuestaWS> call, Throwable t) {
                    Log.d("Retrofit", "Error: " + t.getMessage());
                }
            });
        }
    }

    public  void registrar(View v){
        Intent registrar = new Intent(this, Registro.class);
        startActivity(registrar);
        finish();
    }

    public void gotoLogout(String token, int user_id, String username, String fotoperfil){

            Intent gotologout = new Intent(this, LogoutActivity.class);
            Bundle params = new Bundle();
            params.putString("token", token);
            params.putInt("user_id", user_id);
            params.putString("username", username);
            params.putString("fotoperfil", fotoperfil);
            //System.out.println("Verificacion token: " + token);
            gotologout.putExtras(params);
            //System.out.println("Username: " + username);
            startActivity(gotologout);
            finish();

    }

    public String generateUUID(){
        device_id = UUID.randomUUID().toString();
        return device_id;
    }

    public boolean validar(){
        boolean retorno=true;
        String campo1=user.getText().toString();
        String campo2=password.getText().toString();
        Pattern patterpassword = Pattern.compile(REJEX_PASSWORD);

        if(campo1.length()>8){
            user.setError("Username puede tener maximo 8 caracteres");
            retorno = false;
        }
        if(campo1.length()<4){
            user.setError("Username puede tener minimo 4 caracteres");
            retorno = false;
        }

        if(campo2.length()>12){
            password.setError("Username puede tener maximo 12 caracteres");
            retorno = false;
        }
        if(campo2.length()<6){
            password.setError("Username puede tener minimo 6 caracteres");
            retorno = false;
        }
        if(!patterpassword.matcher(campo2).matches()){
            password.setError("Formato de password invalido");
            retorno = false;
        }
        if(campo1.isEmpty()){
            user.setError("Porfavor introduzca Username");
            retorno = false;
        }
        if(campo2.isEmpty()){

            password.setError("Porfavor introduzca Contraseña");
            retorno = false;
        }
        return retorno;
    }
}
