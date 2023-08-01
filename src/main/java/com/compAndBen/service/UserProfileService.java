package com.compAndBen.service;

import com.example.demo.model.UserMaster;
import com.example.demo.repo.UserMasterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    @Autowired
    private UserMasterRepo userMasterRepo;
    //he mentioned jwttokenUtility class and I think that is not needed at all

    public UserMaster GetUserProfile(String emailid) throws Exception {
        UserMaster profile=this.userMasterRepo.findByEmail(emailid);
        if(profile==null)
        {
            throw new Exception("User not found");

        }
        return this.userMasterRepo.findByEmail(emailid);





    }

}