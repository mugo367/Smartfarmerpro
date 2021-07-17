package com.example.smartfarmer.ui.market;

public class Products {
    String post_id;
    String product_name;
    String product_category;
    String product_maizetype;
    String product_detail;
    String product_date;
    String product_quantity;
    String product_price;
    String product_description;
    String post_time;
    String post_image;
    String product_poster;

    public String getProduct_poster() {
        return product_poster;
    }

    public void setProduct_poster(String product_poster) {
        this.product_poster = product_poster;
    }

    public Products(String product_poster) {
        this.product_poster = product_poster;
    }



    public Products(String post_id, String product_name, String product_category, String product_maizetype, String product_detail, String product_date, String product_quantity, String product_price,String product_description, String post_time, String post_image) {
        this.post_id = post_id;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_maizetype = product_maizetype;
        this.product_date =product_date;
        this.product_detail =product_detail;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.product_description =product_description;
        this.post_time = post_time;
        this.post_image = post_image;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(String product_detail) {
        this.product_detail = product_detail;
    }

    public String getProduct_maizetype() {
        return product_maizetype;
    }

    public void setProduct_maizetype(String product_maizetype) {
        this.product_maizetype = product_maizetype;
    }

    public String getProduct_date() {
        return product_date;
    }

    public void setProduct_date(String product_date) {
        this.product_date = product_date;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public Products(){

    }
}
