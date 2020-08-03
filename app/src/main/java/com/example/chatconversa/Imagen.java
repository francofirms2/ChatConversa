package com.example.chatconversa;

import java.util.Objects;

public class Imagen {

    private String image;
    private String thumbnail;

    public Imagen() {
    }

    public Imagen(String image, String thumbnail) {
        this.image = image;
        this.thumbnail = thumbnail;
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
        Imagen imagen = (Imagen) o;
        return Objects.equals(image, imagen.image) &&
                Objects.equals(thumbnail, imagen.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, thumbnail);
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "image='" + image + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
