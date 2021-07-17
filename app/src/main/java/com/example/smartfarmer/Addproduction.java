package com.example.smartfarmer;

public class Addproduction {
    String id,productionlabel, productiondate,fieldname, maizetype, productiontype,productionquantity, unit, productiondetails;

    public Addproduction(String id, String productionlabel, String productiondate, String fieldname, String maizetype,  String productiontype, String productionquantity,String unit, String productiondetails) {
        this.id = id;
        this.productionlabel = productionlabel;
        this.productiondate = productiondate;
        this.fieldname = fieldname;
        this.maizetype = maizetype;
        this.productiontype = productiontype;
        this.productionquantity = productionquantity;
        this.unit = unit;
        this.productiondetails = productiondetails;
    }

    public Addproduction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductionlabel() {
        return productionlabel;
    }

    public void setProductionlabel(String productionlabel) {
        this.productionlabel = productionlabel;
    }

    public String getProductiondate() {
        return productiondate;
    }

    public void setProductiondate(String productiondate) {
        this.productiondate = productiondate;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getMaizetype() {
        return maizetype;
    }

    public void setMaizetype(String maizetype) {
        this.maizetype = maizetype;
    }

    public String getProductiontype() {
        return productiontype;
    }

    public void setProductiontype(String productiontype) {
        this.productiontype = productiontype;
    }

    public String getProductionquantity() {
        return productionquantity;
    }

    public void setProductionquantity(String productionquantity) {
        this.productionquantity = productionquantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductiondetails() {
        return productiondetails;
    }

    public void setProductiondetails(String productiondetails) {
        this.productiondetails = productiondetails;
    }
}
