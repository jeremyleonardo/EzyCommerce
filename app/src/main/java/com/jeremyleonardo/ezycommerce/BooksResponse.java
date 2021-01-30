package com.jeremyleonardo.ezycommerce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BooksResponse {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("nim")
    @Expose
    private String nim;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("productId")
    @Expose
    private Object productId;
    @SerializedName("credits")
    @Expose
    private String credits;
    @SerializedName("products")
    @Expose
    private List<Book> products = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public List<Book> getProducts() {
        return products;
    }

    public void setProducts(List<Book> products) {
        this.products = products;
    }

}