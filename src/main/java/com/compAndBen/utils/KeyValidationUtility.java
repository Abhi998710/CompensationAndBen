package com.compAndBen.utils;

import com.example.demo.exception.InvalidInputException;
import com.example.demo.exception.KeyValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public class KeyValidationUtility {

    @Autowired
    private Environment env;

    public int checkKeyValidation(Map<String,Object> request, String... keys)
    {
        if(request.size()!=keys.length)
        {
            return -1;
        }
        for(String key:keys) {
            if (request.containsKey(key)) {
                continue;
            }
            else
            {
                return 0;
            }
        }
        return 1;
    }
    //its basically a file validation
    public void uploadUserFileKeyValidation(MultipartFile file,Integer fileid) throws KeyValidationException
    {
        if(file==null)
        {
            throw new KeyValidationException("Give proper key file ");
        }
        if(fileid==null)
        {
            //we can compare primitive to null
            //it should be the object type which could make the impact
            throw new KeyValidationException("Give proper fileid");
        }

    }
    public void setFileStatusKeyValidation(Map<String,Object> request, String... keys) throws  KeyValidationException {
        if (checkKeyValidation(request, keys) == -1) {
            throw new KeyValidationException("Key does not exists");

        }
        if (checkKeyValidation(request, keys) == 0) {
            throw new KeyValidationException("Key is invalid");
        }

        if ((!String.valueOf(request.get("isActive")).equals("true")
                && String.valueOf(request.get("isActive")).equals("false"))
                || (String.valueOf(request.get("isActive")).equals("true")
                && !String.valueOf(request.get("isActive")).equals("false"))) {

        } else {
            throw new KeyValidationException("Provide either a true or false value");
        }
    }
        public void getFileDataKeyValidation(Map<String,Object> request, String... keys) throws KeyValidationException, InvalidInputException {
            if (checkKeyValidation(request, keys) == -1) {
                throw new KeyValidationException("Key does not exists");

            }
            if (checkKeyValidation(request, keys) == 0) {
                throw new KeyValidationException("Key is invalid");
            }
            try {
                Integer.parseInt(String.valueOf(request.get("fileid")));
            } catch (Exception e) {
                throw new InvalidInputException("Provide a numerice fileid");
            }


        }
        public  void deleteDataKeyValidation(Map<String,Object> request, String... keys) throws KeyValidationException {
            if (checkKeyValidation(request, keys) == -1) {
                throw new KeyValidationException("Key does not exists");

            }
            if (checkKeyValidation(request, keys) == 0) {
                throw new KeyValidationException("Key is invalid");
            }


        }











    }


    //as this are validations
    //they need to be kept public

