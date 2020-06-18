package com.example.chatconversa;

import java.util.Objects;

public class RespuestaWSRegistro {

    private int status_code;
    private String message;
    private UserRespuesta data;

    public RespuestaWSRegistro() {

    }

    public RespuestaWSRegistro(int status_code, String message, UserRespuesta data) {
        this.status_code = status_code;
        this.message = message;
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
        RespuestaWSRegistro that = (RespuestaWSRegistro) o;
        return status_code == that.status_code &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_code, message, data);
    }

    @Override
    public String toString() {
        return "RespuestaWSRegistro{" +
                "status_code=" + status_code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
