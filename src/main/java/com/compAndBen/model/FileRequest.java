package com.compAndBen.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;


public class FileRequest {


    @NotNull(message="Please provide valid key for the file id")
    private Integer fileId;

    public FileRequest(@NotNull(message = "Please provide valid key for the file id") Integer fileId, @Valid @NotNull(message = "Please provide valid keys for the columns to update") List<ExcelModel> columnsToUpdate) {
        this.fileId = fileId;
        this.columnsToUpdate = columnsToUpdate;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public List<ExcelModel> getColumnsToUpdate() {
        return columnsToUpdate;
    }

    public void setColumnsToUpdate(List<ExcelModel> columnsToUpdate) {
        this.columnsToUpdate = columnsToUpdate;
    }

    @Valid
    @NotNull(message="Please provide valid keys for the columns to update")
    private List<ExcelModel> columnsToUpdate;

}
