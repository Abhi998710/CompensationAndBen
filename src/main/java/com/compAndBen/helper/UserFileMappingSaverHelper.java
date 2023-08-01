package com.compAndBen.helper;

import com.example.demo.model.FileMaster;
import com.example.demo.model.TempTable;
import com.example.demo.model.UserFileMapping;
import com.example.demo.model.UserMaster;
import com.example.demo.repo.FileMasterRepo;
import com.example.demo.repo.TempTableRepo;
import com.example.demo.repo.UserFileMappingRepo;
import com.example.demo.repo.UserMasterRepo;
import org.apache.catalina.User;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

//what does temp table has is emp_code and filename
//this class is used to save entire UserMaster and FileMaster data into UserFileMapping table
//and temptabel repo and save them all at the end
@Component
public class UserFileMappingSaverHelper {
    @Autowired
    private UserMasterRepo userMasterRepo;
    @Autowired
    private FileMasterRepo fileMasterRepo;
    @Autowired
    private TempTableRepo tempTableRepo;
    @Autowired
    private UserFileMappingRepo userFileMappingRepo;

    //we need to create the arrayList of tempRepo and userFileMappingRepo

    public void insertingToUserFileMappingDb(Map<String,String> map)
    {
        ArrayList<TempTable> tempTableArrayList=new ArrayList<>();
        ArrayList<UserFileMapping> userFileMappingArrayList=new ArrayList<>();
        //iterate through the map that is given to us
        for(Map.Entry<String,String> mapping:map.entrySet())
        {
            //get usermaster
            //and fileMaster from the mappings that are given to us
            String  userId=mapping.getKey();

            String filename=mapping.getValue();
            //I am still doubtFull of the mapping between both of them
            //need to have more detailed view on this
            //and it is needed for now

            //email is casted to single user
            //but I am talking about the list of suer
            Optional<UserMaster> userMaster=userMasterRepo.findById(userId);
            //if it does not exist put it in the tempTable
            if(!userMaster.isPresent())
            {
                //iterate and put the values in the temporary table
                //create the new tempRepo object and all the values accordingly
                TempTable tempTable=new TempTable();
                tempTable.setUser_code(userId);
                tempTable.setFilemaster(fileMasterRepo.findByName(filename));
                //put all the file attributes in the tempTable and see the magic
                tempTableArrayList.add(tempTable);
            }
            //if the user is present add the data into userFileMapping database
            UserMaster userMaster1=userMaster.get();
            FileMaster fileMaster=fileMasterRepo.findByName(filename);
            UserFileMapping userMapping=new UserFileMapping();
            //its not the repo its a normal database to be setted with the values
            userMapping.setUser(userMaster1);
            userMapping.setFileMaster(fileMaster);
            //now add everything to the arrayList that we have declared here earlies
            userFileMappingArrayList.add(userMapping);
            //set the values into the respective table
            //that how we create the database and iterate over the values
            //emtire structure we will get to know in details later
            //and that stands as a necessary point
            //it will set each value in the database to the iteration and
            //and at the end save everything to the table at the end
            //and that is the thing






        }   userFileMappingRepo.saveAll(userFileMappingArrayList);
            tempTableRepo.saveAll(tempTableArrayList);




    }


}