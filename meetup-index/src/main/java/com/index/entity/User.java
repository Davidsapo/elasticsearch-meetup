package com.index.entity;

import com.index.entity.nested.NestedOrder;
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
 * User Entity.
 *
 * @author David Sapozhnik
 */
@Document(indexName = "i_user")
public class User {

    @Id
    private String id;

    @Field(name = "entity_id")
    private UUID userId;

    @Field(name = "full_name", type = FieldType.Text)
    private String fullName;

    @Field(name = "phone", type = FieldType.Keyword)
    private String phone;

    @Field(name = "email", type = FieldType.Keyword)
    private String email;

    @Field(name = "orders", type = FieldType.Nested)
    private final List<NestedOrder> orders = new ArrayList<>();

    @Field(name = "created_at", type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<NestedOrder> getOrders() {
        return orders;
    }
}
