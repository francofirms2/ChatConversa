package com.example.chatconversa;

import java.util.Objects;

public class Constantes {

    private String token;
    private int user_id;
    private String username;
    private int cambio;

    public Constantes() {

    }

    public Constantes(String token, int user_id, String username, int cambio) {
        this.token = token;
        this.user_id = user_id;
        this.username = username;
        this.cambio = cambio;
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

    public int getCambio() {
        return cambio;
    }

    public void setCambio(int cambio) {
        this.cambio = cambio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constantes that = (Constantes) o;
        return user_id == that.user_id &&
                cambio == that.cambio &&
                Objects.equals(token, that.token) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, user_id, username, cambio);
    }

    @Override
    public String toString() {
        return "Constantes{" +
                "token='" + token + '\'' +
                ", user_id=" + user_id +
                ", username='" + username + '\'' +
                ", cambio=" + cambio +
                '}';
    }
}
