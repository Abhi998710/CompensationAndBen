package com.compAndBen.model;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

public class ExcelModel {
    @NotNull(message="Occurence index cannot be null")
    private Integer occurrenceIndex;

    @NotNull(message="columns index and value cannot be null")
    private HashMap<String,String> columnValues;




    public Integer getOccurrenceIndex() {
        return occurrenceIndex;
    }

    public void setOccurrenceIndex(Integer occurrenceIndex) {
        this.occurrenceIndex = occurrenceIndex;
    }

    public HashMap<String, String> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(HashMap<String, String> columnValues) {
        this.columnValues = columnValues;
    }

    public ExcelModel(@NotNull(message = "Occurence index cannot be null") Integer occurrenceIndex, @NotNull(message = "columns index and value cannot be null") HashMap<String, String> columnValues) {
        this.occurrenceIndex = occurrenceIndex;
        this.columnValues = columnValues;
    }

}
