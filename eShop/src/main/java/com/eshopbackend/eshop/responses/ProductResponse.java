package com.eshopbackend.eshop.responses;

import java.util.UUID;

public class ProductResponse {

    private UUID id;
    private String sellerName;
    private String name;
    private double price;
    private String description;
    private String productType;
    private int inStock;
    private String stockStatus;
    
    public ProductResponse(UUID id, String sellerName, String name, double price, String description,
                           String productType, int inStock, String stockStatus) {
        this.id = id;
        this.sellerName = sellerName;
        this.name = name;
        this.price = price;
        this.description = description;
        this.productType = productType;
        this.inStock = inStock;
        this.stockStatus = stockStatus;
    }

    public UUID getId() {
        return id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getProductType() {
        return productType;
    }

    public int getInStock() {
        return inStock;
    }

    public String getStockStatus() {
        return stockStatus;
    }
}

