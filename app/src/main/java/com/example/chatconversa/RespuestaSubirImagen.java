package com.example.chatconversa;

import java.util.Objects;

public class RespuestaSubirImagen {

    private String status_code;
    private String message;
    private Imagen data;

    public RespuestaSubirImagen() {
    }

    public RespuestaSubirImagen(String status_code, String message, Imagen data) {
        this.status_code = status_code;
        this.message = message;
        this.data = data;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Imagen getData() {
        return data;
    }

    public void setData(Imagen data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RespuestaSubirImagen that = (RespuestaSubirImagen) o;
        return Objects.equals(status_code, that.status_code) &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_code, message, data);
    }

    @Override
    public String toString() {
        return "RespuestaSubirImagen{" +
                "status_code='" + status_code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
