package com.compAndBen.model;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
public class TempTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private String user_code;
    @ManyToOne
    private FileMaster filemaster;
    public TempTable()
    {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_code() {
        return user_code;
    }

    public TempTable(int id, String user_code, FileMaster filemaster) {
        this.id = id;
        this.user_code = user_code;
        this.filemaster = filemaster;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public FileMaster getFilemaster() {
        return filemaster;
    }

    public void setFilemaster(FileMaster filemaster) {
        this.filemaster = filemaster;
    }
}
