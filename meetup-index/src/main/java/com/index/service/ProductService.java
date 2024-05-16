package com.index.service;

import com.index.dao.ProductDao;
import com.index.entity.Product;
import com.index.enums.SystemEventType;
import com.index.model.ProductDTO;
import com.index.model.events.ProductCreatedEvent;
import com.index.model.events.SystemEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.index.converter.ProductConverter.toProductDTOs;
import static com.index.enums.SystemEventType.PRODUCT_CREATED;

/**
 * Product Service.
 *
 * @author David Sapozhnik
 */
@Service
public class ProductService extends SystemEventService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<SystemEventType> getPrimaryEventTypes() {
        return List.of(PRODUCT_CREATED);
    }

    @Override
    public List<SystemEventType> getAdditionalEventTypes() {
        return List.of();
    }

    @Override
    public <T extends SystemEvent> void processSystemEvent(T systemEvent) {
        switch (systemEvent.getType()) {
            case PRODUCT_CREATED -> createNewProduct((ProductCreatedEvent) systemEvent);
            default -> throw new IllegalArgumentException("Invalid system event type.");
        }
    }

    /**
     * Find all by ids list.
     *
     * @param productIds the product ids
     * @return the list
     */
    public List<ProductDTO> findAllByIds(List<UUID> productIds) {
        return toProductDTOs(productDao.findByIds(productIds));
    }

    /* Private methods */

    private void createNewProduct(ProductCreatedEvent productCreatedEvent) {
        var product = new Product();
        product.setProductId(productCreatedEvent.getEntityId());
        product.setName(productCreatedEvent.getName());
        product.setPrice(productCreatedEvent.getPrice());
        product.setCreatedAt(productCreatedEvent.getCreatedAt());
        productDao.save(product);
    }
}
