package com.compAndBen.model;

import org.aspectj.weaver.GeneratedReferenceTypeDelegate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;

@Entity
//mapping ahe so its okay
public class UserFileMapping implements Serializable {

    //its sometimes file's decision and user's decision
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserMaster user;
    //this is the mapping class usermaster and filemaster altogether
    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FileMaster fileMaster;

    public FileMaster getFileMaster() {
        return fileMaster;
    }

    public void setFileMaster(FileMaster fileMaster) {
        this.fileMaster = fileMaster;
    }

    public UserFileMapping() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserFileMapping(int id, UserMaster user, FileMaster fileMaster) {
        this.id = id;
        this.user = user;
        this.fileMaster = fileMaster;
    }

    public UserMaster getUser() {
        return user;
    }

    public void setUser(UserMaster user) {
        this.user = user;
    }


}