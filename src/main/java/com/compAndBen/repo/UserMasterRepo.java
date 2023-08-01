package com.compAndBen.repo;

import com.example.demo.model.UserMaster;
import net.bytebuddy.utility.nullability.UnknownNull;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//repository is always the interface
@Repository
public interface UserMasterRepo extends JpaRepository<UserMaster,String>{

    @Query("Select u from UserMaster u where u.emailId=?1")
    UserMaster findByEmail(String email);

    //as there is mapping between both the tables

    //passing id of the owner and extracting whether it is of the specific email id
    @Query(value = "Select * from UserMaster where userid=?1 And user_type_user_type_id=1")
    List<UserMaster> checkTenantExists(String userid);

    boolean existsByEmail(String emailid);




}