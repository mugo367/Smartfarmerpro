package com.example.smartfarmer.ui.Employee;

public class Addreport {

    String name; String date; String task; String problem; String rec; String add;

    public Addreport() {
    }

    public Addreport(String name, String date, String task, String problem, String rec, String add) {
        this.name = name;
        this.date = date;
        this.task = task;
        this.problem = problem;
        this.rec = rec;
        this.add = add;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }
}
