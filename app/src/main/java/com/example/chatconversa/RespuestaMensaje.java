package com.example.chatconversa;

import java.util.List;
import java.util.Objects;

public class RespuestaMensaje {

    private int status_code;
    private String message;
    private List<Mensaje> data;

    public RespuestaMensaje() {
    }

    public RespuestaMensaje(int status_code, String message, List<Mensaje> data) {
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

    public List<Mensaje> getData() {
        return data;
    }

    public void setData(List<Mensaje> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RespuestaMensaje that = (RespuestaMensaje) o;
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
        return "RespuestaMensaje{" +
                "status_code=" + status_code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
