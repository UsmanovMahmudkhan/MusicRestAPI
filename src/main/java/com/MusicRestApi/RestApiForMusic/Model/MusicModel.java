package com.MusicRestApi.RestApiForMusic.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "musics")
public class MusicModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String path;

    public MusicModel() {
    }

    public MusicModel(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
