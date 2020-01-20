package com.example.pagingtest2.pokemon;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Pokemon {
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Pokemon pokemon = (Pokemon) object;
        return Objects.equals(name, pokemon.name) &&
                Objects.equals(url, pokemon.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
