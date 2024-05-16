package com.index.repository;

import com.index.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * User Repository.
 *
 * @author David Sapozhnik
 */
public interface UserRepository extends ElasticsearchRepository<User, String> {
}
