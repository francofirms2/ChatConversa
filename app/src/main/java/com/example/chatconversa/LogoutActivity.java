package com.example.chatconversa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogoutActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logout;
    private Constantes constantes;

    private String token;
    private int user_id;
    private String username;

    private ServicioWeb servicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deslog);
        logout = findViewById(R.id.logout);


        logout.setOnClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                //.client(cliente)
                .build();

        servicio = retrofit.create(ServicioWeb.class);

        Bundle params = getIntent().getExtras();
        //System.out.println("Token Params: " + params.getString("token"));
        token = params.getString("token");
        user_id = params.getInt("user_id");
        username = params.getString("username");

    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(LogoutActivity.this);
        alerta.setMessage("¿Desea cerrar sesión?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alerta, int which) {

                        Logout logout = new Logout(user_id,username);
                        String tokenbearer = "Bearer " + token;
                        Call<RespuestaWS> resp = servicio.logout(logout, tokenbearer);
                        resp.enqueue(new Callback<RespuestaWS>() {
                            @Override
                            public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {
                                if(response.isSuccessful() && response != null && response.body() != null){
                                    RespuestaWS respuesta = response.body();
                                    System.out.println("STATUS CODE: " + response.body().getStatus_code());
                                    Log.d("Retrofit", response.body().getMessage());
                                    gotoLogin();
                                }else {
                                    Gson gson = new Gson();
                                    ErrorResponse respuestaerror = new ErrorResponse();
                                    try {
                                        respuestaerror = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                                        //System.out.println("Response error: " + respuestaerror);
                                        if (respuestaerror.getStatus_code() == 401) {
                                            Log.d("Retrofit", "ERROR: " + respuestaerror.getMessage());
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
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alerta, int which) {
                        alerta.cancel();
                    }
                });

        alerta.setTitle("Logout");
        alerta.show();

    }

    public void gotoLogin(){
        Intent gotologin = new Intent(this, MainActivity.class);
        startActivity(gotologin);
        finish();
    }
    public void stayLogout(){
        Intent stayLogout = new Intent(this, LogoutActivity.class);
        startActivity(stayLogout);
        finish();
    }

}
