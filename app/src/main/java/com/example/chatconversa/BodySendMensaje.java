package com.example.chatconversa;

import java.util.Objects;

public class BodySendMensaje {

    private int user_id;
    private String username;
    private String message;
    private String image;
    private float latitude;
    private float longitude;

    public BodySendMensaje() {
    }

    public BodySendMensaje(int user_id, String username, String message, String image, float latitude, float longitude) {
        this.user_id = user_id;
        this.username = username;
        this.message = message;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodySendMensaje that = (BodySendMensaje) o;
        return user_id == that.user_id &&
                Float.compare(that.latitude, latitude) == 0 &&
                Float.compare(that.longitude, longitude) == 0 &&
                Objects.equals(username, that.username) &&
                Objects.equals(message, that.message) &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, message, image, latitude, longitude);
    }

    @Override
    public String toString() {
        return "BodySendMensaje{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", image='" + image + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
