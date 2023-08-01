package com.compAndBen.controller;


import com.example.demo.factory.ApplicationFactory;
import com.example.demo.helper.EmailIdFromRequestHeader;
import com.example.demo.model.FileMaster;
import com.example.demo.model.FileRequest;
import com.example.demo.model.UserMaster;
import com.example.demo.repo.UserMasterRepo;
import com.example.demo.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.Objects;

public class userProfileController {

    @Autowired
    private EmailIdFromRequestHeader requestHeader;

    @Autowired
    private UserMasterRepo userrepo;
    @Autowired
    private ExcelService excelService;

    @Autowired
    private UserProfileService profile;
    @Autowired
    private Environment env;

    @GetMapping("/getuserprofile")
    public Response<Object> getUserProfile(HttpServletRequest request) throws Exception {

        Long startTime;
        startTime = System.currentTimeMillis();
        System.out.print("Calling user profile controlller");

        try {
            System.out.println("User proile controller called and took time " + System.currentTimeMillis());
            UserMaster user = profile.GetUserProfile(requestHeader.getEmailIdFromRequestHeader(request));
            return (Response<Object>) ApplicationFactory.getInstance().createResponse(env.getProperty("user data retrieved"), HttpStatus.OK, user);
        } catch (Exception e) {
            System.out.print("Error occured;");
            throw e;

        }
    }
    @PostMapping(value="/updateColumn",consumes = "application/json")
    public ResponseEntity<Object> updateTheColumn(FileRequest fileRequest, HttpServletRequest request,
                                                  BindingResult bindingResult) throws Exception
    {

        if(bindingResult.hasErrors())
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApplicationFactory.getInstance()
            .createResponse("Please provide valid keys"+ Objects.requireNonNull(bindingResult.getFieldError()).getField(),HttpStatus.UNPROCESSABLE_ENTITY));
        }
        excelService.updateColumn(fileRequest,request);
        return ResponseEntity.ok(ApplicationFactory.getInstance().createResponse("File updated sucessfulyy",HttpStatus.OK ));
    }

}
