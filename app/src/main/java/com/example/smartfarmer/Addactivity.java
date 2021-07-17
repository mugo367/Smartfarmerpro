package com.example.smartfarmer;

public class Addactivity {

    private String id, taskname, fieldname, startdate, finaldate,employee, equipment,  description;




    public Addactivity(String id, String taskname, String fieldname, String startdate, String finaldate, String employee, String equipment, String description) {
        this.id =  id;
        this.taskname = taskname;
        this.fieldname = fieldname;
        this.startdate = startdate;
        this.finaldate = finaldate;
        this.employee = employee;
        this.equipment = equipment;
        this.description = description;
    }

    public Addactivity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getFinaldate() {
        return finaldate;
    }

    public void setEnddate(String finaldate) {
        this.finaldate = finaldate;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
