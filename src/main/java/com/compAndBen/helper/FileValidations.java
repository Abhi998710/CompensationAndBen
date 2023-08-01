package com.compAndBen.helper;


import com.example.demo.exception.InputNotAcceptableException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.repo.FileMasterRepo;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.poi.openxml4j.opc.internal.ContentType;
import org.aspectj.weaver.JoinPointSignatureIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.management.InvalidAttributeValueException;
import java.util.List;

@Component
public class FileValidations {
    @Autowired
    private FileMasterRepo fileRepo;
    @Autowired
    private TempHelper tempHelper;

    public static final String REGEX_PATTERN="^^[A-Za-z0-9_-]*$";
    //allows the alphabets numbers and everything
    public static Logger logger= LoggerFactory.getLogger(FileValidations.class);
    //the file should be converted to the multipart to look how it basically looks

    public void emptyFileValidation(MultipartFile file) throws InputNotAcceptableException
    {
        if(file.isEmpty()|| file==null)
        {
            logger.error("File Validation failed, no file uploaded");
            throw new InputNotAcceptableException("No file uploaded");

        }


    }
    public void fileTypeValidation(String contentType) throws InvalidInputException
    {
        if(!contentType.equals("\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\"\n"))
        {
            logger.error("File type validation error");
            throw new InvalidInputException("The content type of the file is not valid");
        }

    }
    //this validations have no return type. They are returning nothing. Just validating
    public void fileNameExistsValidation(String filename) throws  InputNotAcceptableException
    {
        boolean b=fileRepo.existByFileName(filename);
        if(b==true)
        {
            //give this is as error as we are validating that no duplicate name should exist.
            logger.error("VALIDATION ERROR:File already exists" );
            throw new InputNotAcceptableException("File already exists");
        }

    }
    public void fileIdNotExistsValidation(int id) throws InputNotAcceptableException
    {
        boolean f=fileRepo.existsById(id);
        if(f==false)
        {
            logger.error("File with the id "+id+" does not exists");
            throw new InputNotAcceptableException("VALIDATION ERROR File with the id "+id+" does not exists\"");
        }


    }
    public void fileSizeValidation(MultipartFile file) throws InputNotAcceptableException
    {
        long fileSize;
        long sizeTOMb;
        fileSize=file.getSize();
        sizeTOMb=fileSize/(1024*1024);
        if(sizeTOMb>4)
        {
            logger.error("File size should be lesser than 4MB");
            throw new InputNotAcceptableException("VALIDATION ERROR: enter the file lesser or equal to 4MB");
        }
    }
    public void fileNameValidation(String filename) throws InvalidInputException,InputNotAcceptableException
    {
        if(filename==null || filename.isEmpty() || filename.length()>255)
        {
            logger.error("Either no file name or too long filename ");
            throw new InputNotAcceptableException("VALIDATION ERROR: Either no file or too long file");

        }
    }
    public void columnHeaderValidation(MultipartFile file) throws Exception{
        List<String> list=tempHelper.traverseColumn(file.getInputStream());
        tempHelper.checkColumnHeader(list);



    }





}