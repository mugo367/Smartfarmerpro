package com.example.smartfarmer.ui.farmrecords;

public class Addfield {
    String id,  fieldname, fieldsize, fieldstatus;



    public Addfield( String id, String fieldname, String fieldsize, String fieldstatus) {
        this.id = id;
        this.fieldname = fieldname;
        this.fieldsize = fieldsize;
        this.fieldstatus = fieldstatus;
    }
    public Addfield() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getFieldsize() {
        return fieldsize;
    }

    public void setFieldsize(String fieldsize) {
        this.fieldsize = fieldsize;
    }

    public String getFieldstatus() {
        return fieldstatus;
    }

    public void setFieldstatus(String fieldstatus) {
        this.fieldstatus = fieldstatus;
    }
}
