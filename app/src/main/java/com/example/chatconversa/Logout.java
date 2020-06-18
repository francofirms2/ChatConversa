package com.example.chatconversa;

import java.util.Objects;

public class Logout {

    private int user_id;
    private String username;

    public Logout() {

    }

    public Logout(int user_id, String username) {
        this.user_id = user_id;
        this.username = username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logout logout = (Logout) o;
        return Objects.equals(user_id, logout.user_id) &&
                Objects.equals(username, logout.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username);
    }

    @Override
    public String toString() {
        return "Logout{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
