package com.example.smartfarmer;

public class User {
    private String id;
    private String Fullname, PhoneNumber, County, SubCounty, Farmname, Fieldsize;
    private String Username;
    private String imageURL;
    private String status;


    public User() {

    }

    public User(String id, String fullname, String phoneNumber, String county, String subCounty, String farmname, String fieldsize, String username, String imageURL, String status) {
        this.id = id;
        Fullname = fullname;
        PhoneNumber = phoneNumber;
        County = county;
        SubCounty = subCounty;
        Farmname = farmname;
        Fieldsize = fieldsize;
        Username = username;
        this.imageURL = imageURL;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getSubCounty() {
        return SubCounty;
    }

    public void setSubCounty(String subCounty) {
        SubCounty = subCounty;
    }

    public String getFarmname() {
        return Farmname;
    }

    public void setFarmname(String farmname) {
        Farmname = farmname;
    }

    public String getFieldsize() {
        return Fieldsize;
    }

    public void setFieldsize(String fieldsize) {
        Fieldsize = fieldsize;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}