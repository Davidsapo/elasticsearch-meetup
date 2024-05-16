package com.content.service;

import com.content.entity.Product;
import com.content.model.ProductDTO;
import com.content.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Product Service.
 *
 * @author David Sapozhnik
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Create product product dto.
     *
     * @param userDTO the user dto
     * @return the product dto
     */
    public ProductDTO createProduct(ProductDTO userDTO) {
        var user = new Product();
        user.setName(userDTO.getName());
        user.setPrice(userDTO.getPrice());
        user.setCreatedAt(LocalDateTime.now());
        productRepository.save(user);
        copyProperties(user, userDTO);
        return userDTO;
    }

    /**
     * Gets all products.
     *
     * @return the all products
     */
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> {
                    var productDTO = new ProductDTO();
                    copyProperties(product, productDTO);
                    return productDTO;
                })
                .toList();
    }

    /**
     * Gets all products.
     *
     * @return the all products
     */
    public List<ProductDTO> getProductsByIds(List<UUID> ids) {
        return productRepository.findAllById(ids).stream()
                .map(product -> {
                    var productDTO = new ProductDTO();
                    copyProperties(product, productDTO);
                    return productDTO;
                })
                .toList();
    }
}
