package com.index.model;

import java.util.UUID;

/**
 * Product DTO.
 *
 * @author David Sapozhnik
 */
public class ProductDTO {

    private UUID productId;
    private String name;

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
}
