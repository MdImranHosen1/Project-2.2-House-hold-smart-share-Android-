package com.example.myapplication.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {

    String productName;
    String ProductPrice;
    String currentDate;
    String currentTime;
    String documentId;
    int TotalQuantity;
    int totalPrice;

    public MyCartModel() {
    }

    public MyCartModel(String productName, String productPrice, String currentDate, String currentTime, int totalQuantity, int totalPrice) {
        this.productName = productName;
        ProductPrice = productPrice;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        TotalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

//    public MyCartModel(String documentId) {
//        this.documentId = documentId;
//    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public int getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
