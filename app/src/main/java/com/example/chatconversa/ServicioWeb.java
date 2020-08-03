package com.example.chatconversa;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServicioWeb {

    @POST("user/login")
    Call<RespuestaWS> login(@Body Login login);

    @POST("user/create")
    Call<RespuestaWSRegistro> create(@Body User user);

    @POST("user/logout")
    Call<RespuestaWS> logout(@Body Logout logout, @Header("Authorization") String token);

    @POST("message/get")
    Call<RespuestaMensaje> getmensajes(@Body Logout logout, @Header("Authorization") String token);
/*
    @POST("message/send")
    Call<RespuestaSendMessage> sendmensaje(@Body BodySendMensaje bodySendMensaje, @Header("Authorization") String token);*/

    @Multipart
    @POST("user/load/image")
    Call<RespuestaSubirImagen> subirimagen(@Part MultipartBody.Part file, @Part("username")RequestBody username,
                                  @Part("user_id")RequestBody user_id, @Header("Authorization")String token);
    @Multipart
    @POST("message/send")
    Call<RespuestaSendMessage> sendmensaje(@Part MultipartBody.Part file, @Part("username")RequestBody username,
                                           @Part("user_id")RequestBody user_id, @Part("message")RequestBody message ,
                                           @Part("latitude")RequestBody latitude,@Part("longitude")RequestBody longitude,
                                           @Header("Authorization")String token);



}
