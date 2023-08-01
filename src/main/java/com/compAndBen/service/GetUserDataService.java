package com.compAndBen.service;

import com.example.demo.repo.FileMasterRepo;
import io.swagger.models.auth.In;
import lombok.extern.flogger.Flogger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scripting.bsh.BshScriptUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import sun.awt.image.ImageWatched;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GetUserDataService {
    Workbook wb = null;
    @Autowired
    Environment env;

    @Autowired
    FileMasterRepo filerepo;

    public List<Integer> getEmpData(int empid, Sheet sheet)
    {
        int columnNo=0;
        List<Integer> ls=new ArrayList<Integer>();
        for (Row row1 : sheet)
        {
            Cell cell=row1.getCell(columnNo);
            if(cell!=null)
            {
                String stringCellValue;
                if(Objects.equals(cell.getCellType().toString(),"NUMERIC"))
                {
                    stringCellValue=String.valueOf((int)cell.getNumericCellValue());
                    stringCellValue = stringCellValue.replaceFirst("^0*", "");
                    if(stringCellValue.equals(String.valueOf(empid)))
                    {
                        int rowNum=row1.getRowNum();
                        ls.add(rowNum);
                    }

                }
                if(Objects.equals(cell.getCellType().toString(),"STRING"))
                {
                    stringCellValue=cell.getStringCellValue();
                    stringCellValue = stringCellValue.replaceFirst("^0*", "");
                    if(stringCellValue.equals(String.valueOf(empid)))
                    {
                        int rowNum=row1.getRowNum();
                        ls.add(rowNum);
                    }


                }
            }


        }






 return ls;
    }

    public HashMap<String,Object> getAllData(int  empid) throws  NullPointerException
    {
        //now have the list acccording to the empoyee id;
        LinkedHashMap<String,Object > columnData=new LinkedHashMap<>();
        HashMap<String,Object> finale=new HashMap<>();
        //hasmap is fast so return the data in the hashmap
        LinkedHashMap<String,Object> mapObject=new LinkedHashMap<>();
        ArrayList<Object> response=new ArrayList<>();
        //also declare map data to organise the data accordingly
        LinkedHashMap<String,Object> mapdata=new LinkedHashMap<>();
        ArrayList<String> columnKeys=new ArrayList<>();
        ArrayList<String> keys=new ArrayList<>();
        //juts get the keys from the first row
        //then get the columns numbers from 0th row
        //you need to have the workbook to manipulate with the sheet
        Sheet sh= (Sheet) wb.getSheetAt(0);
        ArrayList<String> filename=new ArrayList<>();
        //it repo will return the list of files
        List<String > files=filerepo.getFilePath();
        for(String item:files)
        {
            String s=env.getProperty("get.excel.filepath");
            String filePath=s+"/"+item+".xlxs";
            filename.add(filePath);
        }
        if(filename.isEmpty())
        {
            return  finale;
        }
        //get the keys and columnsIndex;
        //create the list and put the columnskey and actual keys into them
        List<String> key=new ArrayList<>();
        List<String> columnkey=new ArrayList<>();


        for(int q=0;q<filename.size();q++)
        {
            String name=filename.get(q);

            //string filename extraction and put it to fis
            String file0=filename.get(q);
           columnData=new LinkedHashMap<String,Object>();
           mapObject=new LinkedHashMap<String,Object>();
           columnKeys=new ArrayList<>();
           keys=new ArrayList<>();


            FileInputStream fis=null;
            //two hashmap they created
            //columnData,keys mapobject and columnKeys]
            try
            {

                 fis=new FileInputStream(filename.get(q));
                 wb=new XSSFWorkbook(fis);

            }
            catch(Exception e)
            {
               //log the file here
                System.out.print("The file is no");

            }
            finally{
                try {
                    if (fis != null) {

                        fis.close();
                    }
                }
                catch (Exception e)
                {
                    System.out.print("Unable to close the file");
                }

        }
            Sheet sheet = (Sheet) wb.getSheetAt(0);
            Row row=sheet.getRow(1);
            //iterate to the cell and put the key values in the keys list and that is important;
            Iterator<Cell>cell =row.cellIterator();
            //initialize the iterator for the cell
            //while loop to be used as I am needed to go to the end of the cell
            while(cell.hasNext())
            {
               Cell cell1=cell.next();
               keys.add(cell1.getStringCellValue());
            }

            Row row1=sheet.getRow(0);
            Iterator<Cell> cell1=row1.cellIterator();
            while(cell1.hasNext())
            {
                Cell cell11=cell1.next();
                columnKeys.add(cell11.getStringCellValue());
            }

            String newkey;
            for(int l=0;l<columnKeys.size();l++)
            {

                if(columnData.containsValue(keys.get(l)))
                {
                    newkey=keys.get(l);
                }
                else
                {
                    newkey=keys.get(l);
                }
                columnData.put(columnkey.get(l),newkey);

            }


            GetUserDataService userdata=new GetUserDataService();
            List<Integer> integers=userdata.getEmpData(empid,sheet);
            //here the employee specific extraction starts
            //you can put map inside arrayList if object reference is given
            int columns;
            ArrayList<Object> data=new ArrayList<>();
            for(Integer in:integers)
            {

                String empKey;
                mapObject=new LinkedHashMap<>();
                Row rowEmp=sheet.getRow(in);
                for(int k=0;k<rowEmp.getLastCellNum();k++) {
                    Cell cell2 = rowEmp.getCell(k);
                    empKey = keys.get(k);
                    if (cell2 == null) {
                        if (mapObject.containsValue(empKey)) {
                            empKey = empKey + "";
                        }
                        mapObject.put(empKey, " ");

                    } else {
                        //all we need to put is string value into the mapobject
                        String celltype = cell2.getCellType().toString();
                        if (empKey == "") {
                            System.out.print("The columns key is empty");
                        } else {
                            switch (celltype) {
                                case "STRING":
                                    if (mapObject.containsValue(empKey)) {
                                        empKey = empKey + " ";
                                        //he wants to

                                    }
                                    mapObject.put(empKey, cell2.getStringCellValue());
                                    break;
                                case "INTEGER":
                                    if (mapObject.containsValue(empKey)) {
                                        empKey = empKey + " ";

                                    }
                                    if (empKey.contains("Date") || empKey.contains("date")) {
                                        Date date = new Date(cell2.getDateCellValue().getTime());
                                        String format =
                                                new SimpleDateFormat("MM-dd-yyyy").format(date);
                                        mapObject.put(empKey, format);
                                    } else {
                                        mapObject.put(empKey, cell2.getNumericCellValue());
                                    }
                                    break;
                                default:
                                    if (mapObject.containsValue(empKey)) {
                                        empKey = empKey + " ";
                                    }
                                    mapObject.put(empKey, " ");

                            }
                        }


                    }
                }
                data.add(mapObject);

            }
            mapdata.put("data",data);
            mapdata.put("filename",name+".xlsx");
            mapdata.put("Header",columnData);
            response.add(mapdata);
            finale.put("response",response);
















            }







        return finale;


        }



    }
