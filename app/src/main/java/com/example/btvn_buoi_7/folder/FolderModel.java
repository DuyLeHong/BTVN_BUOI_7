package com.example.btvn_buoi_7.folder;

import java.io.Serializable;

public class FolderModel implements Serializable {
    private int id;
    private String name;
    private String description;

    public FolderModel(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public FolderModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
