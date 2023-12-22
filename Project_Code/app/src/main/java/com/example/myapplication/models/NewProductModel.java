package com.example.myapplication.models;

public class NewProductModel {
    String  name,type,description,rating,image,district,subDistrict;

    public NewProductModel() {
    }

    public NewProductModel(String name, String type, String description, String rating, String image, String district, String subDistrict) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.rating = rating;
        this.image = image;
        this.district = district;
        this.subDistrict = subDistrict;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
