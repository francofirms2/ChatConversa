package com.example.chatconversa;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ServicioWeb {

    @POST("user/login")
    Call<RespuestaWS> login(@Body Login login);

    @POST("user/create")
    Call<RespuestaWSRegistro> create(@Body User user);

    @POST("user/logout")
    Call<RespuestaWS> logout(@Body Logout logout, @Header("Authorization") String token);

}
