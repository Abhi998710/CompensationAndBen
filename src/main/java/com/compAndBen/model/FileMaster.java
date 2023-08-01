package com.compAndBen.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
//this is the normal fileMaster class
@Entity
public class FileMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String path;
    private Timestamp uploadTime;
    private Timestamp modificationTime;
    @ManyToOne
    @JoinColumn(name = "uploadedby")
    private UserMaster uploadedBy;

    @ManyToOne
    @JoinColumn(name = "modifiedby")
    private UserMaster modifiedBy;
    private boolean isActive;

    public FileMaster() {
        super();
    }

    public FileMaster(String name, String path, UserMaster uploadedBy, UserMaster modifiedBy,
                      boolean isActive) {
        super();
        this.name = name;
        this.path = path;
        this.uploadTime = Timestamp.from(Instant.now());
        this.uploadedBy = uploadedBy;
        this.isActive = isActive;
    }

    public FileMaster(MultipartFile file, String path, UserMaster user, UserMaster user1, boolean isActive) {
    }

    public UserMaster getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(UserMaster uploadedBy) {
        this.uploadedBy = uploadedBy;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Timestamp getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Timestamp modificationTime) {
        this.modificationTime = modificationTime;
    }

    public UserMaster getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UserMaster modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }


}
