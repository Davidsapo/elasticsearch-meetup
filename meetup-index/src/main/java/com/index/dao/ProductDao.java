package com.index.dao;

import com.index.entity.Product;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

/**
 * Product Dao.
 *
 * @author David Sapozhnik
 */
@Repository
public class ProductDao {

    public static final String PRODUCT_ID_FIELD = "entity_id";

    @Autowired
    private ElasticsearchRestTemplate template;

    /**
     * Save user.
     *
     * @param user user entity
     * @return entity.
     */
    public Product save(Product user) {
        return template.save(user);
    }

    /**
     * Find by entity id.
     *
     * @param entityId entity id
     * @return {@link Optional} of generic object
     */
    public Optional<Product> findById(UUID entityId) {
        var query = new NativeSearchQuery(QueryBuilders.matchQuery(PRODUCT_ID_FIELD, resolveUUID(entityId)));
        return template.search(query, Product.class)
                .getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .findFirst();
    }

    /**
     * Find by entity ids.
     *
     * @param entityIds entity ids
     * @return {@link List} of generic objects
     */
    public List<Product> findByIds(Collection<UUID> entityIds) {
        var query = new NativeSearchQuery(QueryBuilders.termsQuery(PRODUCT_ID_FIELD, resolveUUIDs(entityIds)));
        return template.search(query, Product.class)
                .stream()
                .map(SearchHit::getContent)
                .toList();
    }

    /* Helpers */

    private String resolveUUID(UUID uuid) {
        return uuid.toString();
    }

    private Collection<String> resolveUUIDs(Collection<UUID> uuids) {
        return uuids.stream().map(this::resolveUUID).collect(toList());
    }
}