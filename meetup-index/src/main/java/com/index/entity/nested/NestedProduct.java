package com.index.entity.nested;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

/**
 * Product Nested Object.
 *
 * @author David Sapozhnik
 */
public class NestedProduct {

    @Field(name = "product_id")
    private UUID productId;

    @Field(name = "name", type = FieldType.Text)
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
