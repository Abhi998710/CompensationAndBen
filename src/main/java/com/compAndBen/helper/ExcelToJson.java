package com.compAndBen.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.catalina.mapper.Mapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class ExcelToJson {
//private static final Logger logger=LoggerFactory.getLogger(ExcelToJson.class);
    private static final Logger logger = LoggerFactory.getLogger(ExcelToJson.class);
    private final ObjectMapper mapper = new ObjectMapper();
 
       public JsonNode excelToJson(File excel) {
        // hold the excel data sheet wise
        com.fasterxml.jackson.databind.node.ObjectNode excelData = mapper.createObjectNode();
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            // Creating file input stream
            fis = new FileInputStream(excel);

            String filename = excel.getName().toLowerCase();
            if (filename.endsWith(".xls") || filename.endsWith(".xlsx")) {
                // creating workbook object based on excel file format
                if (filename.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(fis);
                } else {
                    workbook = new XSSFWorkbook(fis);
                }

                // Reading each sheet one by one

                Sheet sheet = workbook.getSheetAt(0);
                String sheetName = null;

                List<String> headers = new ArrayList<>();
                ArrayNode sheetData = mapper.createArrayNode();
                int physicalRows = sheet.getPhysicalNumberOfRows();
             
                // Reading each row of the sheet
                for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                    if (j == 0) {
                        continue;
                    } else if (j == 1) {
                        // reading sheet header's name
                        Row row = sheet.getRow(j);
                        for (int k = 0; k < row.getLastCellNum(); k++) {

                            headers.add(row.getCell(k).toString());
                        }
                        continue;
                    } else {
                        Row row = sheet.getRow(j);
                        // reading work sheet data
                        ObjectNode rowData = mapper.createObjectNode();
                        for (int k = 0; k < headers.size(); k++) {
                            Cell cell = row.getCell(k);
                            String headerName = headers.get(k);
                            if (cell != null) {
                                switch (cell.getCellType()) {

                                    case STRING:
                                        rowData.put(headerName, cell.getStringCellValue());
                                        break;
                                    case BOOLEAN:
                                        rowData.put(headerName, cell.getBooleanCellValue());
                                        break;
                                    case NUMERIC:
                                        rowData.put(headerName, cell.getNumericCellValue());
                                        break;
                                    case BLANK:
                                        rowData.put(headerName, "");
                                        break;
                                    default:
                                        rowData.put(headerName, cell.getStringCellValue());
                                        break;
                                }
                            } else {
                                rowData.put(headerName, "");
                            }
                        }
                        sheetData.add(rowData);
                    }
                }
                if (sheetData.isEmpty()) {
                    return null;
                }
                return sheetData;

            } else {
                throw new IllegalArgumentException("File format not supported.");
            }
        } catch (Exception e) {

            logger.error("File format not supported.");
            throw new IllegalArgumentException(e.getMessage());
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    logger.error("workbook error.");
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("File not closed.");
                }
            }

        }
    }

}
