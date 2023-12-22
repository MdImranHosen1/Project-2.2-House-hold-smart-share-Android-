package com.example.myapplication.models;

import java.io.Serializable;

public class ViewAllModel implements Serializable {

    String name;
    String description;
    String rating;
    String img_url;
    String type;
    String price;
    String district;
    String subDistrict;
    String userId;

    public ViewAllModel() {
    }

    public ViewAllModel(String name, String description, String rating, String img_url, String type, String price, String district, String subDistrict, String userId) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.img_url = img_url;
        this.type = type;
        this.price = price;
        this.district = district;
        this.subDistrict = subDistrict;
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
