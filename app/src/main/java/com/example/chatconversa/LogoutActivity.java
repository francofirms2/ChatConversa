package com.example.chatconversa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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



    private Toolbar toolbar;
    private String token;
    private int user_id;
    private String username;
    private String fotoperfil;

    private String token2;
    private int user_id2;
    private String username2;

    private ServicioWeb servicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deslog);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




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
        fotoperfil = params.getString("fotoperfil");

        MensajesFragment msgfrag = new MensajesFragment();
        Bundle params2 = new Bundle();
        params2.putString("token", token);
        params2.putInt("user_id", user_id);
        params2.putString("username", username);
        params2.putString("fotoperfil", fotoperfil);
        msgfrag.setArguments(params2);
        getSupportFragmentManager().beginTransaction().replace(R.id.idfragmentmsg ,
                msgfrag).commit();
    }

    @Override
    public void onClick(View v) {
        /*
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
    */
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deslogitem:
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
                return true;

            case R.id.perfil:
                PerfilFragment perfilFragment = new PerfilFragment();
                Bundle params3 = new Bundle();
                params3.putString("token", token);
                params3.putInt("user_id", user_id);
                params3.putString("username", username);
                params3.putString("fotoperfil", fotoperfil);
                perfilFragment.setArguments(params3);
                getSupportFragmentManager().beginTransaction().replace(R.id.idfragmentmsg, perfilFragment).addToBackStack(null).commit();
                System.out.println("Perfil"); break;

            case R.id.equipodesarrollo:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Franco Ignacio Rodriguez Mendez");
                alert.setTitle("Equipo desarrollador");
                alert.show();break;

        }
        return super.onOptionsItemSelected(item);
    }
}
