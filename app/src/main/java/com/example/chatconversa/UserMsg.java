package com.example.chatconversa;

import java.util.Objects;

public class UserMsg {

    private int user_id;
    private String username;
    private String user_image;
    private String user_thumbnail;

    public UserMsg() {
    }

    public UserMsg(int user_id, String username, String user_image, String user_thumbnail) {
        this.user_id = user_id;
        this.username = username;
        this.user_image = user_image;
        this.user_thumbnail = user_thumbnail;
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

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_thumbnail() {
        return user_thumbnail;
    }

    public void setUser_thumbnail(String user_thumbnail) {
        this.user_thumbnail = user_thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMsg userMsg = (UserMsg) o;
        return user_id == userMsg.user_id &&
                Objects.equals(username, userMsg.username) &&
                Objects.equals(user_image, userMsg.user_image) &&
                Objects.equals(user_thumbnail, userMsg.user_thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, user_image, user_thumbnail);
    }

    @Override
    public String toString() {
        return "UserMsg{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", user_image='" + user_image + '\'' +
                ", user_thumbnail='" + user_thumbnail + '\'' +
                '}';
    }
}
