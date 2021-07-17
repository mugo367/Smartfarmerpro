package com.example.smartfarmer;

public class Addemployee {
    String id; String employeename, dateofemp; String personid; String email; String contact; String designation; String emptype; String emergencyperson; String emergencycontact;

    public Addemployee() {

    }

    public Addemployee( String id, String employeename, String personid, String email, String contact, String designation, String emptype, String dateofemp, String emergencyperson, String emergencycontact) {
        this.id = id;
        this.employeename = employeename;
        this.personid = personid;
        this.email = email;
        this.contact = contact;
        this.designation = designation;
        this.dateofemp = dateofemp;
        this.emptype = emptype;
        this.emergencyperson = emergencyperson;
        this.emergencycontact = emergencycontact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmptype() {
        return emptype;
    }

    public void setEmptype(String emptype) {
        this.emptype = emptype;
    }

    public String getDateofemp() {
        return dateofemp;
    }

    public void setDateofemp(String dateofemp) {
        this.dateofemp = dateofemp;
    }

    public String getEmergencyperson() {
        return emergencyperson;
    }

    public void setEmergencyperson(String emergencyperson) {
        this.emergencyperson = emergencyperson;
    }

    public String getEmergencycontact() {
        return emergencycontact;
    }

    public void setEmergencycontact(String emergencycontact) {
        this.emergencycontact = emergencycontact;
    }
}
