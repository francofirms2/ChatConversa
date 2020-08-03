package com.example.chatconversa;

public class DatosCall {

    private String token;
    private int user_id;
    private String username;

    private static DatosCall datosCall;

    public DatosCall(String token, int user_id, String username) {
        this.token = token;
        this.user_id = user_id;
        this.username = username;
    }

    public static  synchronized  DatosCall getInstance(){
        if(datosCall == null){
            datosCall = new DatosCall();
        }
        return datosCall;
    }

    public DatosCall() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static DatosCall getDatosCall() {
        return datosCall;
    }

    public static void setDatosCall(DatosCall datosCall) {
        DatosCall.datosCall = datosCall;
    }

    @Override
    public String toString() {
        return "DatosCall{" +
                "token='" + token + '\'' +
                ", user_id=" + user_id +
                ", username='" + username + '\'' +
                '}';
    }
}
