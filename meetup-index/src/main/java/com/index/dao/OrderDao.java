package com.index.dao;

import com.index.entity.Order;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

/**
 * Order Dao.
 *
 * @author David Sapozhnik
 */
@Repository
public class OrderDao {

    public static final String ORDER_ID_FIELD = "entity_id";
    public static final String USER_ID_FIELD = "user.user_id";
    public static final String USER_FIELD = "user";
    public static final String PRODUCTS_FIELD = "products";
    public static final String USER_NAME_FIELD = "user.full_name";
    public static final String PRODUCT_NAME_FIELD = "products.name";

    @Autowired
    private ElasticsearchRestTemplate template;

    /**
     * Save order.
     *
     * @param order order entity
     * @return entity.
     */
    public Order save(Order order) {
        return template.save(order);
    }

    /**
     * Save entity list.
     *
     * @param entities {@link Collection} of entities
     * @return {@link List} of entities.
     */
    public List<Order> saveAll(Collection<Order> entities) {
        return entities.stream().map(this::save).collect(toList());
    }

    /**
     * Find by entity id.
     *
     * @param entityId entity id
     * @return {@link Optional} of generic object
     */
    public Optional<Order> findById(UUID entityId) {
        var query = new NativeSearchQuery(QueryBuilders.matchQuery(ORDER_ID_FIELD, resolveUUID(entityId)));
        return template.search(query, Order.class)
                .getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .findFirst();
    }

    /**
     * Find by user id.
     *
     * @param userId entity id
     * @return {@link Optional} of generic object
     */
    public List<Order> findByUserId(UUID userId) {
        var query = new NativeSearchQuery(QueryBuilders
                .nestedQuery(
                        USER_FIELD, QueryBuilders.matchQuery(USER_ID_FIELD, resolveUUID(userId)), ScoreMode.None));
        return template.search(query, Order.class)
                .getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .collect(toList());
    }

    /**
     * Find all by search list.
     *
     * @param search the search
     * @return the list
     */
    public List<Order> findAllBySearch(String search) {

        var query = new NativeSearchQueryBuilder();

        if (nonNull(search)) {
            query.withQuery(QueryBuilders.boolQuery()
                    .should(QueryBuilders
                            .nestedQuery(USER_FIELD, QueryBuilders
                                    .matchPhrasePrefixQuery(USER_NAME_FIELD, search), ScoreMode.None))
                    .should(QueryBuilders
                            .nestedQuery(PRODUCTS_FIELD, QueryBuilders
                                    .matchPhrasePrefixQuery(PRODUCT_NAME_FIELD, search), ScoreMode.None)));
        }

        return template.search(query.build(), Order.class)
                .getSearchHits()
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