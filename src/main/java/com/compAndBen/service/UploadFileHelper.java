package com.compAndBen.service;

import com.example.demo.exception.InputNotAcceptableException;
import com.example.demo.helper.FileValidations;
import com.example.demo.model.UserMaster;
import com.example.demo.model.UserType;
import com.example.demo.repo.UserMasterRepo;
import com.example.demo.repo.UserTypeRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Component
public class UploadFileHelper {

    //also assign a type to the user after finding it exists by email id

    @Autowired
    private UserTypeRepo userTypeRepo;
    @Autowired
    private UserMasterRepo userMasterRepo;

    //upload the file update the file and delete the file
    @Autowired
    private  FileValidations validations;

    public boolean uploadFile(MultipartFile file,String Filename, String filePath) throws
            FileUploadException{
        //if its not on the path, then only you can create
        //always check in the parent whether the path exists or not
        boolean uploadStatus=false;
        Path path= Paths.get(filePath);
        if(!Files.exists(path))
        {
            try {
                Path dirpath = Paths.get(filePath);
                Files.createDirectories(dirpath);
            }
            catch (IOException e)
            {
                throw new FileUploadException("Cannot create the directories for the file");
            }


        }
        //now you need to copy the multipart file on this path
        //copy this file into this inputStream
        try
        {
            Files.copy(file.getInputStream(),Paths.get(filePath+ File.separator+Filename+".xlxs"));
            uploadStatus=true;


        }
        catch (IOException e)
        {

            throw new FileUploadException("unable to upload the file ");
        }

        return uploadStatus;
    }

    public boolean updateFile(MultipartFile file, String filename,String oldfilename,String filepath)
            throws FileUploadException
    {
        //also set the updatestatus to make path visible;
        boolean updateStatus=false;
        //check whether the file exists on the given path
        Path path=Paths.get(filepath);
        if(!Files.exists(path))
        {
            try {
                Path dir = Paths.get(filepath);
                Files.createDirectories(dir);
            }
            catch(IOException e)
            {
                throw new FileUploadException("Cannot create the directory for non existant path");
            }
        }
        try{
            Files.delete(Paths.get(filepath+File.separator+oldfilename+".xlxs"));

        }
        catch (IOException e)
        {
            throw new FileUploadException("Cannot delete the file ");
        }
        //now update the file on the particular location
        try{
            Files.copy(file.getInputStream(),Paths.get(filepath+File.separator+filename+"xlxs"), StandardCopyOption.REPLACE_EXISTING);
            updateStatus=true;

        }
        catch(IOException e)
        {
            throw new FileUploadException("Failed to upload file." + e.getMessage());
        }
        //basically the inputstream of the multipart file is uplaoded in the status


        return updateStatus;
    }
    //do the validations for the upload file and update file
    public void fileUploadValidation(MultipartFile file,String filename) throws Exception
    {
        validations.emptyFileValidation(file);
        validations.fileNameValidation(filename);
        validations.fileTypeValidation(file.getContentType());
        validations.fileSizeValidation(file);
        validations.columnHeaderValidation(file);

    }
    public void fileUpdateValidation(MultipartFile file,String filename,int fileId,String oldFilename) throws Exception
    {
        validations.emptyFileValidation(file);
        validations.fileNameValidation(filename);
        validations.fileTypeValidation(file.getContentType());
        validations.fileSizeValidation(file);
        validations.columnHeaderValidation(file);
        validations.fileIdNotExistsValidation(fileId);
        if(oldFilename.equals(filename))
        {
            validations.fileNameExistsValidation(filename);
        }

    }
    //now u need to put the data from insertEmpCodeAndEmpid, from the excel sheet to the database
    //insert everthing into filemasterRepo and userMasterRepo
    public HashMap<String,String> insertDataFromExcelToDb(InputStream is) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        int emaiid = 0;
        int empcode = 0;
        DataFormatter dataFormatter = new DataFormatter();
        Sheet sh = workbook.getSheetAt(0);
        Row row1 = sh.getRow(1);
        int count = 0;
        //iterate the row and save the index
        for (int i = 0; i < row1.getLastCellNum(); i++) {
            Cell cell = row1.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            //format the cell value to the dataformatter
            String cellValue = dataFormatter.formatCellValue(cell).toLowerCase().replaceAll("\\s", "");
            if (cellValue.equals("email")) {
                emaiid = i;
                count++;
            } else if (cellValue.equals("empcode")) {
                empcode = i;
                count++;

            }
            if (count == 2) {
                break;
            }


        }
        for (int rowindex = 2; rowindex <= sh.getLastRowNum(); rowindex++) {
            //extract the first row and iterate the row
            Row row = CellUtil.getRow(rowindex, sh);
            if (row != null) {
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    String cellValue = dataFormatter.formatCellValue(cell).toLowerCase().replaceAll("//s", "");
                    if (!cellValue.isEmpty()) {
                        Cell emailCell = CellUtil.getCell(row, emaiid);
                        Cell empcodeCell = CellUtil.getCell(row, empcode);
                        String emailCellVal = dataFormatter.formatCellValue(emailCell).toLowerCase().replaceAll("//s", "");
                        String empcodeCellVal = dataFormatter.formatCellValue(empcodeCell).toLowerCase().replaceAll("//s", "");
                        map.put(empcodeCellVal,emailCellVal);
                        if (emailCellVal.isEmpty() || empcodeCellVal.isEmpty()) {
                            throw new InputNotAcceptableException("The table values could not be null");
                        }
                        break;
                    }

                }
            }
        }

        //compare empcode and email value
        //empcode:key
        //email:value

        //map has value of email and employee code
        for (Map.Entry<String, String> mapping : map.entrySet())
        {
            String empcodeCellVal=mapping.getKey();
            String emailCellVal=mapping.getValue();
            //check whether the user exists by the given name
            boolean userexists=userMasterRepo.existsByEmail(emailCellVal);
            //it it does not exists he couldnt be the admin
            if(!userexists)
            {
                //String userId, String emailId, UserType userType--> this are the user attributes
                UserType userRole=new UserType(2,"USER");
                UserMaster userNew=new UserMaster(empcodeCellVal,emailCellVal,userRole);
            }
            else
            {
                UserMaster userNew=userMasterRepo.findByEmail(emailCellVal);
                //will extract the user type of id 103 and set it to the new user;
                Optional<UserType> userType=userTypeRepo.findById(103);
                if(userNew.getUserType().getUserTypeId()==1)
                {
                    userNew.setUserType(userType.get());
                    userMasterRepo.save(userNew);



                }


            }



        }

        //i have returned the map by iterating through values.

        System.out.print("User data is saved successfully");
        //closing the workbook is important and make things work
        workbook.close();
        return map;

        }

        //get the column to which access needs to be given
        public String getCellData(Sheet datatypeSheet,String editableaccesstobegivento,int occurenceIndex)
        {

            HashMap<String,Integer> columns=new HashMap<>();
            Row row1=datatypeSheet.getRow(1);
            datatypeSheet.getRow(1).forEach(
                    cell -> {
                        columns.put(cell.getStringCellValue().toLowerCase().replaceAll("\\s",""),cell.getColumnIndex());
                    }
            );
            return getCellData(datatypeSheet, occurenceIndex,columns.get("editableaccesstobegivento"));
         //iterate to get occurence index and column number of editable access given to
            //you will get the value to whom access needs to be given
            //just occurence index is important, rest of the things doesnt matter much;

            //put the values to access the values and get them back.

        }
        public String getCellData(Sheet dataTypeSheet, int occurenceIndex,Integer columnIndex)
        {
            Cell cell=dataTypeSheet.getRow(occurenceIndex).getCell(columnIndex);
            //initialize the celldata variable, so that you can put the data accordingly,
            String columnData=" ";
            //represent and return the data into string format and that is sureshot
            switch (cell.getCellType()){
                case STRING:
                    columnData=cell.getStringCellValue();
                    break;
                case NUMERIC:
                    //check if the cell has the data format
                    if(DateUtil.isCellDateFormatted(cell))
                    {
                        columnData=String.valueOf(cell.getStringCellValue());

                    }
                    columnData=String.valueOf(cell.getStringCellValue());
                    break;
                case BOOLEAN:
                    columnData=String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    columnData=" ";
                    break;











            }
            return columnData;

        }





    }




