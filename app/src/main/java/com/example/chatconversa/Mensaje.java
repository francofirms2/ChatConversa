package com.example.chatconversa;

import java.util.Objects;

public class Mensaje {

    private int id;
    private String date;
    private String message;
    private float latitude;
    private float longitude;
    private String image;
    private String thumbnail;
    private UserMsg user;

    public Mensaje() {
    }

    public Mensaje(int id, String date, String message, float latitude, float longitude, String image, String thumbnail, UserMsg user) {
        this.id = id;
        this.date = date;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.thumbnail = thumbnail;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public UserMsg getUser() {
        return user;
    }

    public void setUser(UserMsg user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensaje mensaje = (Mensaje) o;
        return id == mensaje.id &&
                Float.compare(mensaje.latitude, latitude) == 0 &&
                Float.compare(mensaje.longitude, longitude) == 0 &&
                Objects.equals(date, mensaje.date) &&
                Objects.equals(message, mensaje.message) &&
                Objects.equals(image, mensaje.image) &&
                Objects.equals(thumbnail, mensaje.thumbnail) &&
                Objects.equals(user, mensaje.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, message, latitude, longitude, image, thumbnail, user);
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", image='" + image + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", user=" + user +
                '}';
    }
}
