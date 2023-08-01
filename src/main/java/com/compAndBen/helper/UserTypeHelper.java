package com.compAndBen.helper;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserMaster;
import com.example.demo.repo.UserMasterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


//lets assumef
@Component
public class UserTypeHelper {
    //here we need to check whether the user exists by email or
    //user exists by id
    //i need to validate the user with the emailid and its id
    @Autowired
    private UserMasterRepo userMasterrepo;
    public void userIdValidation(String userid) throws ResourceNotFoundException
    {
      boolean f=userMasterrepo.existsById(userid);
      if(!f)
      {
          throw new ResourceNotFoundException("VALIDATION ERROR: employeeIDValidation failed: EmployeeID does not exist");


      }


    }
    public void userEmailValidation(String email) throws Exception
    {
        boolean f=userMasterrepo.existsByEmail(email);
        if(!f)
        {
            throw new ResourceNotFoundException("\"VALIDATION ERROR: employeeEmailValidation failed: Employee emailid does not exist\"");


        }
    }
}
