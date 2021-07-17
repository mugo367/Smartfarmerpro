package com.example.smartfarmer;

public class Addincome {
    String id, transactiondate,transactiontype,transactionname,transactioncost,transactiondetails;

    public Addincome(String id, String transactiondate, String transactiontype, String transactionname, String transactioncost, String transactiondetails) {
        this.id = id;
        this.transactiondate = transactiondate;
        this.transactiontype = transactiontype;
        this.transactionname = transactionname;
        this.transactioncost = transactioncost;
        this.transactiondetails = transactiondetails;
    }

    public Addincome() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(String transactiondate) {
        this.transactiondate = transactiondate;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getTransactionname() {
        return transactionname;
    }

    public void setTransactionname(String transactionname) {
        this.transactionname = transactionname;
    }

    public String getTransactioncost() {
        return transactioncost;
    }

    public void setTransactioncost(String transactioncost) {
        this.transactioncost = transactioncost;
    }

    public String getTransactiondetails() {
        return transactiondetails;
    }

    public void setTransactiondetails(String transactiondetails) {
        this.transactiondetails = transactiondetails;
    }
}
