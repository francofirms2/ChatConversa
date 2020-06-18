package com.example.chatconversa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText name;
    private TextInputEditText lastname;
    private TextInputEditText run;
    private TextInputEditText username;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText token_enterprise;
    private TextInputEditText passwordconfirmation;

    private Button registrar;
    private ServicioWeb servicio;

    public static final String REJEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,12}$";
    public static final String REJEX_TOKEN_ENTERPRISE = "^[A-Z\\d]{6,6}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        name =     findViewById(R.id.nameReg);
        lastname = findViewById(R.id.lastnameReg);
        run =     findViewById(R.id.runReg);
        username = findViewById(R.id.usernameReg);
        email =     findViewById(R.id.emailReg);
        password = findViewById(R.id.passwordReg);
        token_enterprise = findViewById(R.id.token_enterpriseReg);

        passwordconfirmation = findViewById(R.id.passwordRegConfirmacion) ;

        registrar = findViewById(R.id.registrar);
        registrar.setOnClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicio = retrofit.create(ServicioWeb.class);
    }

    @Override
    public void onClick(View v) {
        if(validar()) {

            User user = new User(name.getText().toString(), lastname.getText().toString(), run.getText().toString(), username.getText().toString(),
                    email.getText().toString(), password.getText().toString(), token_enterprise.getText().toString());

            System.out.println(user);
            Call<RespuestaWSRegistro> respuesta = servicio.create(user);

            respuesta.enqueue(new Callback<RespuestaWSRegistro>() {
                @Override
                public void onResponse(Call<RespuestaWSRegistro> call, Response<RespuestaWSRegistro> response) {

                    System.out.println("Response: " + response);
                    if (response.isSuccessful() && response != null && response.body() != null) {
                        RespuestaWSRegistro resp = response.body();
                        if (resp.getStatus_code() == 201) {
                            Log.d("Retrofit", resp.getMessage());
                            gotoLogin();
                        }
                    } else {
                        Gson gson = new Gson();
                        ErrorResponseRegistro respuestaerror = new ErrorResponseRegistro();

                        try {
                            respuestaerror = gson.fromJson(response.errorBody().string(), ErrorResponseRegistro.class);
                            System.out.println(respuestaerror);
                            if (respuestaerror.getStatus_code() == 400) {
                                Log.d("Retrofit", respuestaerror.getMessage());
                                if (respuestaerror.getErrors().getEmail() != null) {
                                    Log.d("Retrofit", respuestaerror.getErrors().getEmail().toString());
                                    email.setError("Este Email ya se encuentra en uso");
                                }
                                if (respuestaerror.getErrors().getToken_enterprice() != null) {
                                    Log.d("Retrofit", respuestaerror.getErrors().getToken_enterprice().toString());
                                }
                                if (respuestaerror.getErrors().getUsername() != null) {
                                    Log.d("Retrofit", respuestaerror.getErrors().getUsername().toString());
                                    username.setError("Este Username ya se encuentra en uso");
                                }


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespuestaWSRegistro> call, Throwable t) {
                    Log.d("Retrofit", "Error: " + t.getMessage());
                }
            });
        }
    }
    public  void volver(View v){
        Intent volver = new Intent(this, MainActivity.class);
        startActivity(volver);
        finish();
    }

    public void gotoLogin(){
        Intent gotologin = new Intent(this, MainActivity.class);
        startActivity(gotologin);
        finish();
    }

    public boolean validar(){
        boolean retorno=true;
        String camponame = name.getText().toString();
        String campolastname = lastname.getText().toString();
        String camporun = run.getText().toString();
        String campousername = username.getText().toString();
        String campoemail = email.getText().toString();
        String campopassword = password.getText().toString();
        String campotoken = token_enterprise.getText().toString();
        String campospasswordconfirmation = passwordconfirmation.getText().toString();

        Pattern patternpassword = Pattern.compile(REJEX_PASSWORD);
        Pattern patterntoken = Pattern.compile(REJEX_TOKEN_ENTERPRISE);

        if(camponame.length() > 60){
            name.setError("El Nombre no puede sobrepasar los 60 caracteres");
            retorno = false;
        }
        if(campolastname.length() > 60){
            lastname.setError("El Apellido no puede sobrepasar los 60 caracteres");
            retorno = false;
        }
        if(camporun.length() > 8){
            run.setError("El RUN no puede sobrepasar los 8 caracteres");
            retorno = false;
        }
        if(camporun.length() < 7){
            run.setError("El RUN no puede tener menos de 7 caracteres");
            retorno = false;
        }
        if(campousername.length()>8){
            username.setError("El Username no puede sobrepasar los 8 caracteres");
            retorno = false;
        }
        if(campousername.length()<4){
            username.setError("El Username no puede sobrepasar los 8 caracteres");
            retorno = false;
        }
        if(campoemail.length() > 50){
            email.setError("El Email no puede sobrepasar los 50 caracteres");
            retorno = false;
        }
        if(campopassword.length()>12){
            password.setError("La Contraseña no puede pasar los 12 caracteres");
            retorno = false;
        }
        if(campopassword.length()<6){
            password.setError("La Contraseña no puede tener menos de 6 caracteres");
            retorno = false;
        }
        if(!patternpassword.matcher(campopassword).matches()){
            password.setError("Formato de contraseña invalido");
            retorno = false;
        }

        //CONFIRMACION DE PASSWORD
        System.out.println(campopassword);
        System.out.println(campospasswordconfirmation);
        if(!campopassword.equals(campospasswordconfirmation)){
            password.setError("Las constraseñas no coinciden");
            passwordconfirmation.setError("Las constraseñas no coinciden");
            retorno = false;
        }
        if(!patterntoken.matcher(campotoken).matches()){
            token_enterprise.setError("Formato de token invalido");
            retorno = false;
        }

        //CONFIRMACION DE PASSWORD

        if(camponame.isEmpty()){
            name.setError("Porfavor introduzca Nombre");
            retorno = false;
        }
        if(campolastname.isEmpty()){
            lastname.setError("Porfavor introduzca Apellido");
            retorno = false;
        }
        if(camporun.isEmpty()){
            run.setError("Porfavor introduzca RUN");
            retorno = false;
        }
        if(campousername.isEmpty()){
            username.setError("Porfavor introduzca Username");
            retorno = false;
        }
        if(campoemail.isEmpty()){
            email.setError("Porfavor introduzca Email");
            retorno = false;
        }
        if(campopassword.isEmpty()){
            password.setError("Porfavor introduzca Password");
            retorno = false;
        }
        if(campotoken.isEmpty()){
            token_enterprise.setError("Porfavor introduzca Token");
            retorno = false;
        }
        return retorno;
    }
}
