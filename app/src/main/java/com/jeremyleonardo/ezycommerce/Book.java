package com.jeremyleonardo.ezycommerce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Book {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private BigDecimal price;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("inCart")
    @Expose
    private Boolean inCart;
    @SerializedName("category")
    @Expose
    private String category;

    private Integer qty;

    public Book() { }

    public Book(Integer id, String name, String description, BigDecimal price, String author, String type, String img, String category, Boolean inCart) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.author = author;
        this.type = type;
        this.img = img;
        this.category = category;
        this.inCart = inCart;
    }

    public Book(Integer id, String name, String description, BigDecimal price, String author, String type, String img, String category, Integer qty, boolean inCart) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.author = author;
        this.type = type;
        this.img = img;
        this.category = category;
        this.inCart = inCart;
        this.qty = qty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Boolean getInCart() {
        return inCart;
    }

    public void setInCart(Boolean inCart) {
        this.inCart = inCart;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStars() {
        return "★★★★✩"; // hardcoded because API does not provide the rating
    }

    public void setQty(Integer qty){
        this.qty = qty;
    }

    public Integer getQty() {
        return qty;
    }

}