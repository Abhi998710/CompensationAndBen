package com.compAndBen.repo;

import com.example.demo.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;


//this interface has the primary key the
//mapping is manytoone
public interface UserTypeRepo extends JpaRepository<UserType,Integer> {
}