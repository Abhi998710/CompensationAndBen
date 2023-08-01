package com.compAndBen.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="UserMaster")
public class UserMaster {

    @Id
    @Column(length = 20)
    private String userId;
    @Column(nullable = false,unique = true,length = 40)
    private String emailId;
    //as the users are more the mapping should be manytoone
    //many user will have particularity as tenant and client
    @ManyToOne
    private UserType userType;

    public UserMaster(String userId, String emailId, UserType userType) {
        this.userId = userId;
        this.emailId = emailId;
        this.userType = userType;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

