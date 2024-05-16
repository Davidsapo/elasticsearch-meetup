package com.index.entity.nested;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

/**
 * User Nested Object.
 *
 * @author David Sapozhnik
 */
public class NestedUser {

    @Field(name = "user_id")
    private UUID userId;

    @Field(name = "full_name", type = FieldType.Text)
    private String fullName;

    @Field(name = "phone", type = FieldType.Keyword)
    private String phone;

    @Field(name = "email", type = FieldType.Keyword)
    private String email;

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
}
