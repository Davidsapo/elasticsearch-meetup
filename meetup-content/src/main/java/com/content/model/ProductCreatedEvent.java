package com.content.model;

import com.content.enums.SystemEventType;

/**
 * Product Created Event.
 *
 * @author David Sapozhnik
 */
public class ProductCreatedEvent extends SystemEvent {

    private Double price;
    private String name;

    public ProductCreatedEvent() {
        super(SystemEventType.PRODUCT_CREATED);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
