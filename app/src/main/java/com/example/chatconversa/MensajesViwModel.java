package com.example.chatconversa;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MensajesViwModel extends ViewModel  {

    private String token;
    private int user_id;
    private String username;

    ServicioWeb servicio;

    private MutableLiveData<List<Mensaje>> mensajelist;

    public LiveData<List<Mensaje>> getMensajes(String token, int user_id, String username){

        if(mensajelist == null){
            mensajelist = new MutableLiveData<List<Mensaje>>();
            loadmensajes(token,user_id,username);
        }

        return mensajelist;
    }

    private void loadmensajes(String token, int user_id,String username ){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" http://chat-conversa.unnamed-chile.com/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicio = retrofit.create(ServicioWeb.class);


        Logout logout = new Logout(user_id,username);
        String tokenbearer = "Bearer " + token;
        Call<RespuestaMensaje> resp = servicio.getmensajes(logout, tokenbearer);
        resp.enqueue(new Callback<RespuestaMensaje>() {
            @Override
            public void onResponse(Call<RespuestaMensaje> call, Response<RespuestaMensaje> response) {
                if(response.isSuccessful() && response != null && response.body() != null){
                    mensajelist.postValue(response.body().getData());

                }else {
                    System.out.println("ERRRORR  EN LLAMADAAA");
                }
            }

            @Override
            public void onFailure(Call<RespuestaMensaje> call, Throwable t) {

            }
        });
    }

}
