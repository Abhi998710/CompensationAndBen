package com.compAndBen.constant;

public enum constant {

    EMPLOYEE_ID("employeeid"), E_ID("eid"), EMP_CODE("empcode"), EMAIL(
            "email"), EDITABLE_ACCESS_GIVEN_TO("editableaccesstobegivento"), COLUMNS_EDITABLE(
            "columnseditable"), ACCESS_GIVEN_TO("accesstobegivento");

    private final String string;


    constant(String string) {
        this.string=string;
    }
    public String getString( )
    {
        return string;
    }

}
