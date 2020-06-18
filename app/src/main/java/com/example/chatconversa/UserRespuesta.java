package com.example.chatconversa;

import java.util.Objects;

public class UserRespuesta {

    private int id;
    private String name;
    private String lastname;
    private String username;
    private String run;
    private String email;
    private String image;
    private String thumbnail;

    public UserRespuesta() {

    }

    public UserRespuesta(int id, String name, String lastname, String username, String run, String email, String image, String thumbnail) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.run = run;
        this.email = email;
        this.image = image;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRespuesta that = (UserRespuesta) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(username, that.username) &&
                Objects.equals(run, that.run) &&
                Objects.equals(email, that.email) &&
                Objects.equals(image, that.image) &&
                Objects.equals(thumbnail, that.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, username, run, email, image, thumbnail);
    }

    @Override
    public String toString() {
        return "UserRespuesta{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", run='" + run + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
