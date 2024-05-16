package com.purchase.model;

import java.util.UUID;

/**
 * @author David Sapozhnik
 */
public class ProductDTO {

    private UUID productId;
    private String name;
    private Double price;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
