package com.compAndBen.helper;

@Component
public class FileMasterHelper {

    private int id;
    private String name;
    private UserMaster uploadedBy;
    private UserMaster modifiedBy;
    private Boolean isActive;

    public FileMasterHelper() {
        super();
    }

    public FileMasterHelper(int id, String name, UserMaster uploadedBy, UserMaster modifiedBy,
                            boolean isActive) {
        super();
        this.id = id;
        this.name = name;
        this.uploadedBy = uploadedBy;
        this.modifiedBy = modifiedBy;
        this.isActive = isActive;
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

    public UserMaster getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(UserMaster uploadedBy) {
        this.uploadedBy = uploadedBy;
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

    public void setIsActive(Boolean boolean1) {
        this.isActive = boolean1;
    }
}