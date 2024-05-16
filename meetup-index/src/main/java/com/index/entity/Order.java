package com.index.entity;

import com.index.entity.nested.NestedProduct;
import com.index.entity.nested.NestedUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Order Entity.
 *
 * @author David Sapozhnik
 */
@Document(indexName = "i_order")
public class Order {

    @Id
    private String id;

    @Field(name = "entity_id")
    private UUID orderId;

    @Field(name = "amount_paid", type = FieldType.Double)
    private Double amountPaid;

    @Field(name = "status", type = FieldType.Keyword)
    private String status;

    @Field(name = "user", type = FieldType.Nested)
    private NestedUser user;

    @Field(name = "products", type = FieldType.Nested)
    private final List<NestedProduct> products = new ArrayList<>();

    @Field(name = "created_at", type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NestedUser getUser() {
        return user;
    }

    public void setUser(NestedUser user) {
        this.user = user;
    }

    public List<NestedProduct> getProducts() {
        return products;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
