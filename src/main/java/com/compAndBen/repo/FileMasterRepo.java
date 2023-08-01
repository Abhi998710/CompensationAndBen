package com.compAndBen.repo;

import com.example.demo.model.FileMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FileMasterRepo extends JpaRepository<FileMaster,Integer> {
    boolean existByFileName(String filename);
    //if you want to return the file's complete attributes
    FileMaster findByName(String filename);

    //this is very important query to modify the filename and entire thing correctly
    //and that suits me at the point that I am gonna make the beautifull career in it industry

    @Query(value="update file_master set modifiedBy=?1,modifiedTime=?2, name=?3",nativeQuery = true)
    FileMaster updateFile(long userid, Timestamp time,String name);
    //get filepaths of the files which are active at this time and that is important
    @Query(value="select name from file_master where isActive=true",nativeQuery = true)
    List<String> getFilePath();

    //get the filenames of the files which are active at this moment
    @Query(value="select name from file_master where id=:id and isActive=true",nativeQuery = true)
    public String getFilePath(Object fileid);
    //we also need to set the due date as the factor of file
    @Transactional
    @Modifying
    @Query(value="update file_master f set f.isActive=0,f.due_date=null where f.due_date<=CURRENT_DATE",nativeQuery = true)
    public void DeactivateFile();

}