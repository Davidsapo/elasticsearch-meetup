package com.index.repository;

import com.index.entity.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Order Repository.
 *
 * @author David Sapozhnik
 */
public interface OrderRepository extends ElasticsearchRepository<Order, String> {
}
