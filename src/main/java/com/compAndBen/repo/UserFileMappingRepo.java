package com.compAndBen.repo;

import com.example.demo.model.UserFileMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserFileMappingRepo extends JpaRepository<UserFileMapping, Integer> {

    @Query(value = "select * from user_file_mapping where user_employee_id=?1",nativeQuery = true)
    ArrayList<UserFileMapping> userList(String userId);

    @Query(value="select * from user_file_mapping where file_id=?1",nativeQuery = true)
    ArrayList<UserFileMapping> fileList(int fileId);

    @Transactional
    @Modifying
    @Query(value="Delete from user_file_mapping where file_master_id=:id",nativeQuery = true)
    void delete1(int id);


//I forgot to create the file master repository


}