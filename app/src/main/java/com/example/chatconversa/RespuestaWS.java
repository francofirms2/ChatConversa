package com.example.chatconversa;

import java.util.Objects;

public class RespuestaWS {

    private int status_code;
    private String message;
    private String token;
    private UserRespuesta data;

    public RespuestaWS() {

    }

    public RespuestaWS(int status_code, String message, String token, UserRespuesta data) {
        this.status_code = status_code;
        this.message = message;
        this.token = token;
        this.data = data;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserRespuesta getData() {
        return data;
    }

    public void setData(UserRespuesta data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RespuestaWS that = (RespuestaWS) o;
        return status_code == that.status_code &&
                Objects.equals(message, that.message) &&
                Objects.equals(token, that.token) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_code, message, token, data);
    }

    @Override
    public String toString() {
        return "RespuestaWS{" +
                "status_code=" + status_code +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", data=" + data +
                '}';
    }
}
