package com.index.entity.nested;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

/**
 * Order Nested Object.
 *
 * @author David Sapozhnik
 */
public class NestedOrder {

    @Field(name = "order_id")
    private UUID orderId;

    @Field(name = "amount_paid", type = FieldType.Double)
    private Double amountPaid;

    @Field(name = "status", type = FieldType.Keyword)
    private String status;

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
}
