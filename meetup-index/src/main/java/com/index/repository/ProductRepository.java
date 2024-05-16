package com.index.repository;

import com.index.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Product Repository.
 *
 * @author David Sapozhnik
 */
public interface ProductRepository extends ElasticsearchRepository<Product, String> {
}
