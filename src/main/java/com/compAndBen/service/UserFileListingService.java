package com.compAndBen.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.helper.EmailIdFromRequestHeader;
import com.example.demo.jwttoken.jwtTokenUtility;
import com.example.demo.model.TempTable;
import com.example.demo.model.UserFileMapping;
import com.example.demo.model.UserMaster;
import com.example.demo.repo.FileMasterRepo;
import com.example.demo.repo.TempTableRepo;
import com.example.demo.repo.UserFileMappingRepo;
import com.example.demo.repo.UserMasterRepo;
import com.example.demo.utils.dbUtility;
import javafx.beans.binding.ObjectExpression;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserFileListingService {
    @Autowired
    private jwtTokenUtility jwtTokenUtility;

    @Autowired
    private dbUtility dbUtility;

    @Autowired
    private FileMasterRepo fileMasterRepo;

    @Autowired
    private UserMasterRepo userMasterRepo;

    @Autowired
    private UserFileMappingRepo userFileMappingRepo;

    @Autowired
    private TempTableRepo tempTableRepo;

    //darne ka nahi

    @Autowired
    private EmailIdFromRequestHeader emailIdFromRequestHeader;
    //iterate filemaster and convert into arraylist
    //one would be string and other will be the object
    //everything is saved in UserMasterRepo and FileMasterRepo
    //to display user and file in single entity he have created
    public ArrayList<Map<String,Object>> userListing(HttpServletRequest request) throws Exception {
        String email = emailIdFromRequestHeader.getEmailIdFromRequestHeader(request);
        //dbUtility will check for all file validations and make the changes accordingly
        UserMaster userMaster = dbUtility.userMaster(email);
        if (userMaster == null) {
            throw new ResourceNotFoundException("Not user is found");

        }
        //it will throw the exception if user does not exist\
        //whatever the type we are passing,
        //we need to add it in the database
        ArrayList<Map<String, Object>> fileMasterArrayList = new ArrayList<>();
        //add the userFileMapping here
        //map will consists of userFileMapping
        //list will consists of whole list
        //you can also check conversely in the field and see whether the user exists or not
        //get the temptable data will be used further to extract the file data

        ArrayList<TempTable> tempTable = tempTableRepo.getData(userMaster.getUserId());
        for (TempTable a : tempTable) {
            Optional<UserMaster> userMaster1 = userMasterRepo.findById(userMaster.getUserId());
            if (!userMaster1.isPresent()) {
                break;
            }
            UserFileMapping userFileMapping = new UserFileMapping();
            userFileMapping.setUser(userMaster1.get());
            userFileMapping.setFileMaster(a.getFilemaster());
            //save this into userFileMapping repo
            userFileMappingRepo.save(userFileMapping);
            tempTableRepo.delete(a);
        }
        //display the attributes
        ArrayList<UserFileMapping> userFileMappings = userFileMappingRepo.userList(userMaster.getUserId());
        //display the fileMappings now
        System.out.print(userFileMappings);
        for (UserFileMapping a : userFileMappings) {
            if (!a.getFileMaster().isActive()) {
                continue;

            }
            Map<String, Object> map = addMap(a);
            if (!fileMasterArrayList.contains(map)) {
                fileMasterArrayList.add(map);
            }





        }
        return fileMasterArrayList;

    }
    public Map<String,Object> addMap(UserFileMapping a) throws  Exception
    {
        //display id of the file for the particular user and then see
        //how it works
        System.out.print(a.getFileMaster().getId());
        //get specific file attributes like id name description and duedate and
        //make them work accordingly
        Map<String,Object> map=new LinkedHashMap<>();
        map.put("Fileid",a.getFileMaster().getId());
        map.put("filename",a.getFileMaster().getName());
        map.put("fileDescription",a.getFileMaster().getFileDescription());
        if(a.getFileMaster().getDueDate()==null)
        {
            map.put("DueDate","");

        }
        else
        {
            //create the data format and put inside it
            String dueDate=String.valueOf(a.getFileMaster().getDueDate());
            String inputDateFormat = "yyyy-MM-dd";
            String outputDateFormat = "dd-MM-yyyy";
            SimpleDateFormat inputsdf = new SimpleDateFormat(inputDateFormat);
            SimpleDateFormat outputsdf = new SimpleDateFormat(outputDateFormat);
            //for the data, parse the input and format the output and see the changes there
            Date date=inputsdf.parse(dueDate);
            dueDate=outputsdf.format(date);
            map.put("DueDate",dueDate);

        }




        return  map;




    }







}