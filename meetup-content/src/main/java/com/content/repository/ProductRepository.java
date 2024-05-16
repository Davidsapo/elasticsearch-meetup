package com.content.repository;

import com.content.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author David Sapozhnik
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
